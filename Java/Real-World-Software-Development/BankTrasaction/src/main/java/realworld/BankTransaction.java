package realworld;

import realworld.parser.Notification;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class BankTransaction {
    private final LocalDate date;
    private final double amount;
    private final String description;

    public BankTransaction(final LocalDate date, final double amount, final String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void validation() {
        if (validate().hasErrors()) {
            throw new IllegalArgumentException(validate().errorMessage());
        }
    }

    public Notification validate() {
        final Notification notification = new Notification();

        if (date == null || date.toString().isEmpty()) {
            notification.addError("date is required");
        }

        try {
            LocalDate.parse(date.toString());
        } catch (DateTimeParseException e) {
            notification.addError("date format is invalid");
        }

        if (amount <= 0) {
            notification.addError("amount must be greater than zero");
        }

        if (description == null || description.isEmpty()) {
            notification.addError("description is required");
        }

        if (this.description.length() >= 100) {
            notification.addError("The description is too long");
        }

        return notification;
    }

    @Override
    public String toString() {
        return "BankTransaction{" +
                "date=" + date +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankTransaction that = (BankTransaction) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(date, that.date) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, description);
    }
}
