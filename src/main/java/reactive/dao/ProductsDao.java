package reactive.dao;

import java.util.List;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import reactive.model.Product;
import reactive.model.User;
import rx.Observable;

public class ProductsDao {
    private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private final MongoCollection<Document> collection = client.getDatabase("test").getCollection("products");

    public Observable<Success> insertProduct(Product product) {
        return collection.insertOne(product.asDocument());
    }

    public Observable<User> getAllUsers() {
        return collection.find().toObservable().map(User::new);
    }

    public Observable<Product> getAllProducts() {
        return collection.find().toObservable().map(Product::new);
    }
}
