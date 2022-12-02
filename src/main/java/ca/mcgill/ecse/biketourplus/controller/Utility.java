package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;

import java.util.List;

public class Utility {

//	public static void main(String[] args) {
//		String s = "AntoineDeng@gmail.com";
//		System.out.println(validateEmail(s));
//	}
	public static String validateEmail(String email) {
		if(email.isEmpty()) return "Email cannot be empty";
		if(email.equals("manager@btp.com")) return "Email cannot be manager@btp.com";
		if(email.contains(" ")) return "Email must not contain any spaces";
		if(!email.contains("@")) return "Invalid email";
		String[] tokens = email.split("@");
		//System.out.println(tokens.length);
		if(tokens[0].equals("")) return "Invalid email";
		if(tokens.length!=2) return "Invalid email";
		String pattern = "^(.+)(\\S+)$";
		for(int i=0; i<pattern.length(); i++) {
			if (tokens[0].contains(pattern.charAt(i)+"")) return "Invalid email";
		}
//		if(tokens[0].contains(pattern)) return "Invalid email";
		String[] token2 = (tokens[1]).split("\\.");
		//System.out.println(token2.length);
		//System.out.println(token2[0]);
		if(token2.length!=2) return "Invalid email";
		if(!token2[1].equals("com")) return "Invalid email";
		if(token2[0].equals("")) return "Invalid email";
//		if(token2[0].contains(pattern)) return "Invalid email";
		for(int i=0; i<pattern.length(); i++) {
			if (token2[0].contains(pattern.charAt(i)+"")) return "Invalid email";
		}
		BikeTourPlus b1 = BikeTourPlusApplication.getBikeTourPlus();
		List<Guide> existingGuides = b1.getGuides();
//		for (Guide g : existingGuides) {
//			if (g.getEmail().equals(email)) return "Email already linked to a guide account";
//		}
		int i = 0;
		for (Guide g : existingGuides) {
			if (g.getEmail().equals(email)) i++;
			if (i == 2) return "Email already linked to a guide account";
		}
		List<Participant> existingParticipants = b1.getParticipants();
		for (Participant p: existingParticipants) {
			if (p.getEmail().equals(email)) return "Email already linked to a participant account";
		}
		System.out.println("ok");
		return "ok";
	}

	//Compute the total cost of equipment of a participant
	public static int getTotalCostItem(Participant p) {
		int aTotalCostForBookableItem = 0;
		if (p.hasBookedItems() == false) return aTotalCostForBookableItem;
		for (BookedItem booked: p.getBookedItems()) {
			if (Gear.class.isInstance(booked.getItem())) {
				aTotalCostForBookableItem += ((Gear)booked.getItem()).getPricePerWeek()*p.getNrWeeks()*booked.getQuantity();
			}
			else if (Combo.class.isInstance(booked.getItem())) {
				List<ComboItem> comboList = ((Combo)booked.getItem()).getComboItems();
				double discount = ((Combo)booked.getItem()).getDiscount();
				if (!p.getLodgeRequired()) discount = 0;
				for (ComboItem c: comboList) {
					aTotalCostForBookableItem += (1-discount/100)*(c.getQuantity()*c.getGear().getPricePerWeek()*p.getNrWeeks()*booked.getQuantity());
				}
			}
		}
		return aTotalCostForBookableItem;
	}
	public static boolean checkAvail(Participant p, Guide g, BikeTourPlus btp) {
		List<BikeTour> btl = g.getBikeTours();
		int[] gAvail = new int[btp.getNrWeeks()+1];
		boolean assign = true;
		int start = 0;
		int size = 0;
		for (int i = 1; i <= btp.getNrWeeks(); i++) {
			gAvail[i]=1;
			size++;
		}
		for (BikeTour bt: btl) {
			for (int i = bt.getStartWeek(); i <= bt.getEndWeek(); i++) {
				gAvail[i]=0;
				size--;
			}
		}
		if (size < p.getNrWeeks()) return false;
		for (int i = p.getWeekAvailableFrom(); i <= p.getWeekAvailableUntil()-(p.getNrWeeks()-1); i++) {
			assign = true;
			for (int j = 0; j < p.getNrWeeks();j++) {
				if (gAvail[i+j]!=1) {
					assign = false;
					break;
				}
			}
			if (assign) {
				start = i;
				break;
			}
		}
		if (assign == false) return false;
		BikeTour newBT = new BikeTour(btp.getBikeTours().size()+1, start, start+p.getNrWeeks()-1, g, btp);
		newBT.addParticipant(p);
		p.assign(p);
		return true;
	}
}
