package reactive.service;

import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import reactive.dao.UsersDao;
import reactive.model.Currency;
import reactive.model.User;
import rx.Observable;

public class UserService {
    private final UsersDao usersDao;

    public UserService(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public Observable<String> onCreate(HttpServerRequest<ByteBuf> request) {
        var login = request.getQueryParameters().get("login").get(0);
        var currencyRaw = request.getQueryParameters().get("currency").get(0);
        var currency = switch (currencyRaw) {
            case "RUB" -> Currency.RUB;
            case "EUR" -> Currency.EUR;
            case "USD" -> Currency.USD;
            default -> null;
        };
        if (currency == null) {
            return Observable.just("Error: unknown currency");
        }
        return usersDao.insertUser(new User(login, currency)).map(Objects::toString);
    }

}
