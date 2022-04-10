package reactive.model;


import org.bson.Document;

public class User {
    private String login;
    private Currency currency;

    public User(String login, Currency currency) {
        this.login = login;
        this.currency = currency;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Document asDocument() {
        return new Document("id", login).append("currency", currency.toString());
    }

    public User(Document doc) {
        this(doc.getString("id"), Currency.valueOf(doc.getString("currency")));
    }
}
