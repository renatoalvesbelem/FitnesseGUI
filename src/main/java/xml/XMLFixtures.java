package xml;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class XMLFixtures {

    public Fixtures fixtures;
    XStream xStream = new XStream();

    public XMLFixtures() {
        xStream.alias("fixtures", Fixtures.class);
        try {
            fixtures = (Fixtures) xStream.fromXML(new FileInputStream(new File("fixtures.xml")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Fixtures getFixtures() {
        return fixtures;
    }

    public static void main(String args[]) {
      XMLFixtures xmlFixtures = new XMLFixtures();
        xmlFixtures.getFixtures();
    }
}

