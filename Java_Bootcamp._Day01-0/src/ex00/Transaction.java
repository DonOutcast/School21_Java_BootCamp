import java.util.UUID;

public class Transaction {
    private UUID Identifier;
    private User Recipient;
    private User Sender;
    private Category TransferCategory;
    private Integer TransferAmount;

    public enum Category {
        DEBIT, CREDIT
    }

    public Transaction(User recipient, User sender, Category transferCategory, Integer transferAmount) {
        if ((transferAmount > 0 && transferCategory == Category.CREDIT) ||
                (transferAmount < 0 && transferCategory == Category.DEBIT)) {
            System.err.println("Wrong transfer");
        }
        else if ((transferCategory == Category.DEBIT && sender.getBalance() < transferAmount) ||
                (transferCategory == Category.CREDIT && sender.getBalance() < -transferAmount)) {
            System.err.println("Insufficient " + sender.getName() + " balance");
        }
        else {
            this.Identifier = UUID.randomUUID();
            this.Recipient = recipient;
            this.Sender = sender;
            this.TransferCategory = transferCategory;
            this.TransferAmount = transferAmount;
        }
    }

    public UUID getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(UUID identifier) {
        Identifier = identifier;
    }

    public User getRecipient() {
        return Recipient;
    }

    public void setRecipient(User recipient) {
        this.Recipient = recipient;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        this.Sender = sender;
    }

    public Category getTransferCategory() {
        return TransferCategory;
    }

    public void setTransferCategory(Category transferCategory) {
        this.TransferCategory = transferCategory;
    }

    public Integer getTransferAmount() {
        return TransferAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Identifier=" + Identifier +
                ", Recipient=" + Recipient +
                ", Sender=" + Sender +
                ", TransferCategory=" + TransferCategory +
                ", TransferAmount=" + TransferAmount +
                '}';
    }

    public void setTransferAmount(Integer transferAmount) {
        this.TransferAmount = transferAmount;
    }
}
