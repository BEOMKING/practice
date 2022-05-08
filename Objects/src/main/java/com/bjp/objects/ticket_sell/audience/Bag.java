package com.bjp.objects.ticket_sell.audience;

import com.bjp.objects.ticket_sell.ticket.Invitation;
import com.bjp.objects.ticket_sell.ticket.Ticket;

public class Bag {

    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Bag (long amount) {
        this(null, amount);
    }

    public Bag (Invitation invitation, long amount) {
        this.amount = amount;
        this.invitation = invitation;
    }

    public boolean hasInvitation() {
        return invitation != null;
    }

    public boolean hasTicket() {
        return ticket != null;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }

}
