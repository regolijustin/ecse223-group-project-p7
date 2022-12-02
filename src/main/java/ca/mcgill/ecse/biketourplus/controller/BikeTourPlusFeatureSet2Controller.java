package ca.mcgill.ecse.biketourplus.controller;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.persistence.BTPPersistence;

/**
 * @author justinregoli (Justin Regoli)
 *
 */

public class BikeTourPlusFeatureSet2Controller {

	private static BikeTourPlus currentBTP = BikeTourPlusApplication.getBikeTourPlus();

	/**
	 * Updates the BikeTourPlus general information including the start date, number of weeks in the season and
	 * the price of a guide per week
	 *
	 * @param startDate  start date of the biking season
	 * @param nrWeeks   number of weeks in the biking season
	 * @param priceOfGuidePerWeek  price of an individual guide per week
	 * @return empty string if successful, error message otherwise
	 */
	public static String updateBikeTourPlus(Date startDate, int nrWeeks, int priceOfGuidePerWeek) {
		int newYear = startDate.getYear();
		int currentYear = currentBTP.getStartDate().getYear();


		if (nrWeeks >= 0) currentBTP.setNrWeeks(nrWeeks);
		else return "The number of riding weeks must be greater than or equal to zero";

		if (priceOfGuidePerWeek >= 0) currentBTP.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
		else return "The price of guide per week must be greater than or equal to zero";

		if (startDate != null && (newYear >= currentYear)) currentBTP.setStartDate(startDate);
		else return "The start date cannot be from previous year or earlier";
		BTPPersistence.save();
		return "";
	}

	/**
	 * Deletes the participant associated to the specified email address
	 *
	 * @param email email address of the participant to be deleted
	 */
	public static void deleteParticipant(String email) {
		List<Participant> participants = currentBTP.getParticipants();
		for (Participant participant : participants){
			if (participant.getEmail().equalsIgnoreCase(email)) {
				participant.delete();
				BTPPersistence.save();
				return;
			}
		}
	}

	/**
	 * Deletes the Combo specified by its name
	 * @param name name of the combo to be deleted
	 */
	public static void deleteCombo(String name) {
		for (Combo combo: currentBTP.getCombos()) {
			if (combo.getName().equalsIgnoreCase(name)) {
				combo.delete();
				BTPPersistence.save();
				return;
			}
		}
	}

}