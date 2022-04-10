package reactive;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.netty.protocol.http.server.HttpServer;
import reactive.dao.ProductsDao;
import reactive.dao.UsersDao;
import reactive.service.ProductService;
import reactive.service.UserService;
import rx.Observable;

public class Main {
    public static void main(String[] args) {
        var usersDao = new UsersDao();
        var productsDao = new ProductsDao();
        var userService = new UserService(usersDao);
        var productService = new ProductService(productsDao, usersDao);
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    var path = req.getDecodedPath();
                    return switch (path) {
                        case "/create" -> resp.writeString(userService.onCreate(req));
                        case "/get-all" -> resp.writeString(productService.getProducts(req));
                        case "/create-product" -> resp.writeString(productService.createProduct(req));
                        default -> resp.writeString(Observable.just("Not found"));
                    };
                })
                .awaitShutdown();
    }
}
