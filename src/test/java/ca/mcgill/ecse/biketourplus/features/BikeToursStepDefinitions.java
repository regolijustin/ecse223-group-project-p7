package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ca.mcgill.ecse.biketourplus.controller.ExtraFeatures.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BikeToursStepDefinitions {
  private BikeTourPlus btp;
  private String error;

  @Given("the following BikeTourPlus system exists:")
  public void the_following_bike_tour_plus_system_exists(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    btp = BikeTourPlusApplication.getBikeTourPlus();
    btp.setStartDate(java.sql.Date.valueOf(rows.get(0).get("startDate")));
    btp.setNrWeeks(Integer.parseInt(rows.get(0).get("nrWeeks")));
    btp.setPriceOfGuidePerWeek(Integer.parseInt(rows.get(0).get("priceOfGuidePerWeek")));
    error = "";
  }

  @Given("the following guides exist in the system:")
  public void the_following_guides_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      btp.addGuide(columns.get("email"), columns.get("password"), columns.get("name"), columns.get("emergencyContact"));
    }
  }

  @Given("the following participants exist in the system:")
  public void the_following_participants_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      btp.addParticipant(columns.get("email"), columns.get("password"), columns.get("name"), columns.get("emergencyContact"), Integer.parseInt(columns.get("nrWeeks")), Integer.parseInt(columns.get("weeksAvailableFrom")), Integer.parseInt(columns.get("weeksAvailableUntil")), Boolean.parseBoolean(columns.get("lodgeRequired")), null, 0);
    }
  }

  @When("the administrator attempts to initiate the bike tour creation process")
  public void the_administrator_attempts_to_initiate_the_bike_tour_creation_process() {
    callController(initiateBikeTour());
  }

  @Then("the following bike tours shall exist in the system:")
  public void the_following_bike_tours_shall_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    List<BikeTour> BikeTours = btp.getBikeTours();
    boolean exist = false;
    for (Map<String, String> columns : rows) {
      BikeTour b = BikeTour.getWithId(Integer.parseInt(columns.get("id")));
      if (b == null) {
        System.out.println("can't find id");
        assertTrue(false);
      }
      exist = Integer.parseInt(columns.get("startWeek")) == b.getStartWeek() && Integer.parseInt(columns.get("endWeek")) == b.getEndWeek() && columns.get("guide").equals(b.getGuide().getEmail());
      if (!exist) {
        System.out.println("param wrong");
        assertTrue(false);
      }
      List<String> participants = Arrays.asList(columns.get("participants").split(","));
      for (Participant p: b.getParticipants()){
        System.out.println("emails: "+p.getEmail());
      }
      for(BikeTour bt: btp.getBikeTours()){
        System.out.println("biketour");
        for (Participant p: bt.getParticipants()){
          System.out.println(p.getEmail());
        }
      }
      for (String p : participants) {
        exist = false;
        for (Participant par : b.getParticipants()) {
          if (par.getEmail().equals(p)) {
            exist = true;
            break;
          }
        }
        if (!exist) {
          System.out.println("!exist??");
          assertTrue(false);
        }
      }
//      for (BikeTour b : BikeTours) {
//        List<String> participants = Arrays.asList(columns.get("participants").split(","));
//        for (String p : participants) {
//          exist = false;
//          for (Participant par : b.getParticipants()) {
//            if (par.getEmail().equals(p)) {
//              exist = true;
//              break;
//            }
//          }
//          if (!exist) break;
//        }
//        if (!exist) break;
//        exist = Integer.parseInt(columns.get("id")) == b.getId() && Integer.parseInt(columns.get("startWeek")) == b.getStartWeek() && Integer.parseInt(columns.get("endWeek")) == b.getEndWeek() && columns.get("guide").equals(b.getGuide().getEmail());
//      }
    }
    assertTrue(true);
  }

  @Then("the participant with email {string} shall be marked as {string}")
  public void the_participant_with_email_shall_be_marked_as(String string, String string2) {
    Participant par = (Participant) User.getWithEmail(string);
    assertNotNull(par);
    assertEquals(string2, par.getStatusFullName());
  }

  @Then("the number of bike tours shall be {string}")
  public void the_number_of_bike_tours_shall_be(String string) {
    assertEquals(Integer.parseInt(string), btp.getBikeTours().size());
  }

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    assertTrue(error.contains(string));
  }

  @Given("the following pieces of gear exist in the system:")
  public void the_following_pieces_of_gear_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      btp.addGear(columns.get("name"), Integer.parseInt(columns.get("pricePerWeek")));
    }
  }

  @Given("the participant with email {string} has cancelled their tour")
  public void the_participant_with_email_has_cancelled_their_tour(String string) {
    for (Participant p : btp.getParticipants()){
      if (p.getEmail().equalsIgnoreCase(string)) {
        //tried something, not sure if that's good
        if (p.getStatus().equals(Participant.Status.Assigned) || p.getStatus().equals(Participant.Status.Paid) || p.getStatus().equals(Participant.Status.Started)) {
          p.cancel(p);
        }
        break;
      }
    }
  }


  @Given("the following combos exist in the system:")
  public void the_following_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      Combo c = new Combo(columns.get("name"), Integer.parseInt(columns.get("discount")), btp);
      String str = columns.get("items");
      String q = columns.get("quantity");
      List<String> quantities = Arrays.asList(q.split(","));
      List<String> items = Arrays.asList(str.split(","));
      String item;
      int quantity;
      for (int i = 0; i < items.size(); i++) {
        item = items.get(i);
        quantity = Integer.parseInt(quantities.get(i));
        List<Gear> gears = btp.getGear();
        Gear g1 = null;
        for (Gear g : gears) {
          if (g.getName().equals(item)) {
            g1 = g;
          }
        }
        c.addComboItem(quantity, btp, g1);
      }
    }
  }

  @Given("the following participants request the following pieces of gear:")
  public void the_following_participants_request_the_following_pieces_of_gear(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      ((Participant)Participant.getWithEmail(columns.get("email"))).addBookedItem(Integer.parseInt(columns.get("quantity")), btp, Gear.getWithName(columns.get("gear")));
    }
  }

  @Given("the following participants request the following combos:")
  public void the_following_participants_request_the_following_combos(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      ((Participant)Participant.getWithEmail(columns.get("email"))).addBookedItem(Integer.parseInt(columns.get("quantity")), btp, Combo.getWithName(columns.get("gear")));
    }
  }

  @Given("the participant with email {string} is banned")
  public void the_participant_with_email_is_banned(String string) {
    for (Participant p : btp.getParticipants()){
      if (p.getEmail().equalsIgnoreCase(string)) {
        //tried something, not sure if that's good
        if (p.getStatus().equals(Participant.Status.NotAssigned)) {
          p.assign(p);
          p.startBikeTour(p);
        }
        if (p.getStatus().equals(Participant.Status.Assigned)) {
          p.startBikeTour(p);
        }
        break;
      }
    }
  }

  @Given("the participant with email {string} has started their tour")
  public void the_participant_with_email_has_started_their_tour(String string) {
    for (Participant p : btp.getParticipants()){
      if (p.getEmail().equalsIgnoreCase(string)) {
        //tried something, not sure if that's good
        if (p.getStatus().equals(Participant.Status.NotAssigned)) {
          p.assign(p);
          p.pay(p);
          p.startBikeTour(p);
        }
        if (p.getStatus().equals(Participant.Status.Assigned)) {
          p.pay(p);
          p.startBikeTour(p);
        }
        if (p.getStatus().equals(Participant.Status.Paid)) {
          p.startBikeTour(p);
        }
        break;
      }
    }
  }

  @Given("the participant with email {string} has paid for their tour")
  public void the_participant_with_email_has_paid_for_their_tour(String string) {
    for (Participant p : btp.getParticipants()){
      if (p.getEmail().equalsIgnoreCase(string)) {
        //tried something, not sure if that's good
        if (p.getStatus().equals(Participant.Status.NotAssigned)) {
          p.assign(p);
          p.pay(p);
        }
        if (p.getStatus().equals(Participant.Status.Assigned)) {
          p.pay(p);
        }
        break;
      }
    }
  }

  @Given("the following bike tours exist in the system:")
  public void the_following_bike_tours_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      BikeTour b1 = btp.addBikeTour(Integer.parseInt(columns.get("id")),Integer.parseInt(columns.get("startWeek"))
              ,Integer.parseInt(columns.get("endWeek")), ((Guide)Guide.getWithEmail(columns.get("guide"))));
      List<String> pEmails = Arrays.asList(columns.get("participants").split(","));
      for (String e: pEmails){
        Participant p = ((Participant) Participant.getWithEmail(e));
        b1.addParticipant(p);
        p.assign(p);
        System.out.println(p.getStatusFullName());
      }
    }

  }

  @Given("the participant with email {string} has finished their tour")
  public void the_participant_with_email_has_finished_their_tour(String string) {
    for (Participant p : btp.getParticipants()){
      if (p.getEmail().equalsIgnoreCase(string)) {
        //tried something, not sure if that's good
        if (p.getStatus().equals(Participant.Status.NotAssigned)) {
          p.assign(p);
          p.pay(p);
          p.startBikeTour(p);
          p.endBikeTour(p);
        }
        if (p.getStatus().equals(Participant.Status.Assigned)) {
          p.pay(p);
          p.startBikeTour(p);
          p.endBikeTour(p);
        }
        if (p.getStatus().equals(Participant.Status.Paid)) {
          p.startBikeTour(p);
          p.endBikeTour(p);
        }
        if (p.getStatus().equals(Participant.Status.Started)) {
          p.endBikeTour(p);
        }
        break;
      }
    }
  }

  @When("the manager attempts to cancel the tour for email {string}")
  public void the_manager_attempts_to_cancel_the_tour_for_email(String string) {
    callController(cancel(string));
  }

  @When("the manager attempts to finish the tour for the participant with email {string}")
  public void the_manager_attempts_to_finish_the_tour_for_the_participant_with_email(
      String string) {
    callController(finish(string));
  }

  @When("the manager attempts to start the tours for week {string}")
  public void the_manager_attempts_to_start_the_tours_for_week(String string) {
    callController(start(Integer.parseInt(string)));
  }

  @When("the manager attempts to confirm payment for email {string} using authorization code {string}")
  public void the_manager_attempts_to_confirm_payment_for_email_using_authorization_code(
      String string, String string2) {
    callController(pay(string, string2));
  }

//  @Then("the participant with {string} shall be marked as {string}")
//  public void the_participant_with_shall_be_marked_as(String string, String string2) {
//    Participant par = (Participant) User.getWithEmail(string);
//    assertNotNull(par);
//    assertEquals(string2, par.getStatusFullName());
//  }

  @Then("a participant account shall not exist with email {string}")
  public void a_participant_account_shall_not_exist_with_email(String string) {
    for (Participant p : btp.getParticipants()){
      if (p.getEmail().equalsIgnoreCase(string)) {
        assertTrue(false);
      }
    }
  }

  @Then("the number of participants shall be {string}")
  public void the_number_of_participants_shall_be(String string) {
    assertEquals(Integer.parseInt(string), btp.getParticipants().size());
  }


  @Then("a participant account shall exist with email {string} and a refund of {string} percent")
  public void a_participant_account_shall_exist_with_email_and_a_refund_of_percent(String string,
      String string2) {
    Participant p1 = null;
    for(Participant p: btp.getParticipants()){
      if(p.getEmail().equalsIgnoreCase(string)) {
        p1 = p;
        break;
      }
    }
    assertNotNull(p1);
    assertEquals(Integer.parseInt(string2), p1.getRefundedPercentageAmount());
  }

  @Then("a participant account shall exist with email {string} and authorization code {string}")
  public void a_participant_account_shall_exist_with_email_and_authorization_code(String string,
      String string2) {
    Participant p1 = null;
    for(Participant p: btp.getParticipants()){
      if(p.getEmail().equalsIgnoreCase(string)) {
        p1 = p;
        break;
      }
    }
    assertNotNull(p1);
    assertEquals(string2, p1.getAuthorizationCode());
  }

//  @Then("the participant account with email {string} shall be marked as {string}")
//  public void the_participant_account_with_email_shall_be_marked_as(String string, String string2) {
//    Participant p1 = null;
//    for(Participant p: btp.getParticipants()){
//      if(p.getEmail().equalsIgnoreCase(string)) {
//        p1 = p;
//        break;
//      }
//    }
//    assertNotNull(p1);
//    assertEquals(string2, p1.getStatusFullName());
//  }

  private void callController(String result) { // Taken from tutorial 6
    if (!result.isEmpty()) {
      error += result;
    }
  }
}
