package reactive.dao;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import reactive.model.User;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;

public class UsersDao {
    private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private final MongoCollection<Document> collection = client.getDatabase("test").getCollection("users");

    public Observable<Success> insertUser(User user) {
        return collection.insertOne(user.asDocument());
    }

    public Observable<User> getAllUsers() {
        return collection.find().toObservable().map(User::new);
    }

    public Observable<User> getUser(String login) {
        return collection.find(eq("id", login)).toObservable().map(User::new);
    }
}
