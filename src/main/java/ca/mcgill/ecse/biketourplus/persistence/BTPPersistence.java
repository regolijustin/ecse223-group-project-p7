package ca.mcgill.ecse.biketourplus.persistence;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;

import java.sql.Date;

public class BTPPersistence {
    private static String filename = "data.json";
    private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.biketourplus");

    public static void setFilename(String filename) {
        BTPPersistence.filename = filename;
    }
    public static void save() {
        save(BikeTourPlusApplication.getBikeTourPlus());
    }
    public static void save(BikeTourPlus btp) {
        serializer.serialize(btp, filename);
    }

    public static BikeTourPlus load() { //from tutorial
        var btp = (BikeTourPlus) serializer.deserialize(filename);
        // model cannot be loaded - create empty BTP
        if (btp == null) {
            btp = new BikeTourPlus(new Date(0), 0, 0);
        } else {
            btp.reinitialize();
        }
        return btp;
    }
}
