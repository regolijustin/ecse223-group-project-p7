package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

import java.util.List;

public class ExtraFeatures {
    private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();
    /*1. Initiate the bike tour creation process for all guides and participants
    2. Pay for a participant’s trip
    3. Start all trips for a specific week
    4. Finish a participant’s trip
    5. Cancel a participant’s trip */

    public static String initiateBikeTour() {
        List<Guide> gl = btp.getGuides();
        List<Participant> pl = btp.getParticipants();
        for (Guide g: gl) {
            for (Participant p: pl) {
                if (p.getStatus() != Participant.Status.Assigned) {
                    for (BikeTour bt: btp.getBikeTours()) {
                        if (p.getNrWeeks() == bt.getEndWeek()-bt.getStartWeek()+1 && p.getWeekAvailableFrom() <= bt.getStartWeek() && p.getWeekAvailableUntil() >= bt.getEndWeek()) {
                        //if(true) {
                            p.setBikeTour(bt);
                            //bt.addParticipant(p);
                            p.assign(p);
                            break;
                        }
                    }
                    Utility.checkAvail(p,g,btp);
                }
            }
        }
        for(Participant p: btp.getParticipants()){
            if(p.getStatus()!=Participant.Status.Assigned) return "At least one participant could not be assigned to their bike tour";
        }
        return "";
    }
    public static String pay(String email, String code) {
        Participant p = null;
        for (Participant participant : btp.getParticipants()){
            if (participant.getEmail().equalsIgnoreCase(email)) {
                p = participant;
                break;
            }
        }
        if (p == null) return "Participant with email address "+email+" does not exist";
        if (p.getStatus() == Participant.Status.NotAssigned) return "The participant has not been assigned to their tour";
        if (p.getStatus() == Participant.Status.Paid) return "The participant has already paid for their tour";
        if (p.getStatus() == Participant.Status.Started) return "The participant has already paid for their tour";
        if (p.getStatus() == Participant.Status.Banned) return "Cannot pay for tour because the participant is banned";
        if (p.getStatus() == Participant.Status.Cancelled) return "Cannot pay for tour because the participant has cancelled their tour";
        if (p.getStatus() == Participant.Status.Finished) return "The participant has already paid for their tour";
        if (code.equalsIgnoreCase("")) return "Invalid authorization code";
        p.pay(p);
        p.setAuthorizationCode(code);
        try {
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }
    public static String start(int s) {
        for (BikeTour bt: btp.getBikeTours()) {
            if (bt.getStartWeek() == s) {
                for (Participant p: bt.getParticipants()) {
                    if (p.getStatus() == Participant.Status.Started) return "Cannot start tour because the participant has already started their tour";
                    if (p.getStatus() == Participant.Status.Banned) return "Cannot start tour because the participant is banned";
                    if (p.getStatus() == Participant.Status.Cancelled) return "Cannot start tour because the participant has cancelled their tour";
                    if (p.getStatus() == Participant.Status.Finished) return "Cannot start tour because the participant has finished their tour";
                    p.startBikeTour(p);
                }
            }
        }
        try {
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }
    public static String finish(String email) {
        Participant part = null;
        for (Participant p: btp.getParticipants()) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                part = p;
                if (p.getStatus() == Participant.Status.NotAssigned) return "Cannot finish a tour for a participant who has not started their tour";
                if (p.getStatus() == Participant.Status.Assigned) return "Cannot finish a tour for a participant who has not started their tour";
                if (p.getStatus() == Participant.Status.Paid) return "Cannot finish a tour for a participant who has not started their tour";
                if (p.getStatus() == Participant.Status.Banned) return "Cannot finish tour because the participant is banned";
                if (p.getStatus() == Participant.Status.Cancelled) return "Cannot finish tour because the participant has cancelled their tour";
                if (p.getStatus() == Participant.Status.Finished) return "Cannot finish tour because the participant has already finished their tour";
                p.endBikeTour(p);
                break;
            }
        }
        if (part == null) return "Participant with email address "+email+" does not exist";
        try {
            BTPPersistence.save();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return "";
    }
    public static String cancel(String email) {
        Participant part = null;
        for (Participant p: btp.getParticipants()) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                part = p;
                if (p.getStatus() == Participant.Status.Banned) return "Cannot cancel tour because the participant is banned";
                if (p.getStatus() == Participant.Status.Cancelled) return "Cannot cancel tour because the participant has already cancelled their tour";
                if (p.getStatus() == Participant.Status.Finished) return "Cannot cancel tour because the participant has finished their tour";
                if (p.getStatus() == Participant.Status.Paid) p.setRefundedPercentageAmount(50);
                if (p.getStatus() == Participant.Status.Started) p.setRefundedPercentageAmount(10);
                p.cancel(p);
                break;
            }
        }
        if (part == null) return "Participant with email address "+email +" does not exist";
        return "";
    }
}
