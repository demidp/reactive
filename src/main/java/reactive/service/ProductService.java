package reactive.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import reactive.dao.ProductsDao;
import reactive.dao.UsersDao;
import reactive.model.Currency;
import reactive.model.Product;
import rx.Observable;

public class ProductService {
    private final ProductsDao productsDao;
    private final UsersDao usersDao;
    private final static Map<Currency, Double> currencyRate = Map.of(Currency.USD, 70.0, Currency.EUR, 80.0,
            Currency.RUB, 1.0);

    public ProductService(ProductsDao productsDao, UsersDao usersDao) {
        this.productsDao = productsDao;
        this.usersDao = usersDao;
    }

    public Observable<String> createProduct(HttpServerRequest<ByteBuf> req) {
        var params = req.getQueryParameters();
        return productsDao.insertProduct(new Product(
                params.get("name").get(0),
                Double.parseDouble(params.get("price").get(0))
        )).map(Objects::toString);
    }


    public Observable<String> getProducts(HttpServerRequest<ByteBuf> req) {
        var login = req.getQueryParameters().get("login").get(0);
        return usersDao.getUser(login).first()
                .flatMap(user ->
                        productsDao.getAllProducts().map(
                                it -> {
                                    it.setCost((it.getCost() / currencyRate.get(user.getCurrency())));
                                    return it;
                                })).toList()
                .map(Objects::toString);
    }
}
