package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.application.*;

import java.util.ArrayList;
import java.util.List;


public class AccessGear {

    public static Gear getWithName(String name) {
        return (Gear) Gear.getWithName(name);
    }

    public static int getPricePerWeek (String gearName) {
        Gear gear = AccessGear.getWithName(gearName);
        return gear.getPricePerWeek();
    }
    
    public static List<String> getGears() {
      BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();
      List<Gear> gearList = btp.getGear();
      List<String> gearNameList = new ArrayList<String>();
      for (Gear g : gearList) {
          if (g.equals(null)) {
              gearList.remove(g);
          } else {
              gearNameList.add(g.getName());
          }

      }
        return gearNameList;
    }

}
