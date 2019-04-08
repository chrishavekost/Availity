package com.availity;

import jdk.internal.util.xml.impl.Input;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EnrollmentHelper {
    /**
     * Class to help parse enrollment files.
     * Takes in CSV files that include a User Id (string), First and Last name (string), Version (integer),
     * and Insurance Company (string).
     * Separates enrollees by insurance company into separate files.
     * Sorts contents of each new file by last and first name (ascending).
     * Removes duplicate User Ids for the same insurance company, keeping highest version.
     * Test CSV files were made with a header row and are in the Excel CSV format.
     */
    public static void main(String[] args) {
        File[] fileListing = new com.availity.FileLoader().getFiles("CSV");
        for(File f : fileListing) {
            try {
                InputStream input = new FileInputStream(f);
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(br);
                List<Enrollee> eList = new ArrayList<Enrollee>();
                Set<String> insuranceCompanies = new HashSet<String>();

                HashMap<String, HashSet<Enrollee>> map = new HashMap<String, HashSet<Enrollee>>();

                for(CSVRecord r : records) {
                    // Create new Enrollee, capture fields
                    Enrollee e = new Enrollee();
                    e.setUserId(r.get(0)); // "User Id" not working, index 0 is equivalent
                    e.setFirstName(r.get("First Name"));
                    e.setLastName(r.get("Last Name"));
                    e.setVersion(r.get("Version"));
                    e.setInsuranceCompany(r.get("Insurance Company"));

                    

                    /*insuranceCompanies.add(e.getInsuranceCompany());
                    eList.add(e);*/
                }
                /* TODO: manipulate eList. Only want to write to a file once, so separate into Insurance company
                 * TODO: Function to map enrollee to company. Use Company HashMap<UserId, List<Enrollees>>
                 * TODO: Function to iterate over each HashMap. If List size greater than one, compare each enrollee
                 *  version number, keep top number enrollee
                 * TODO: Create another new data container or manipulate existing HashMap. Each List of enrollees should
                 *  now be of size 1, so if we can flatten to <Id, Enrollee
                 * All different enrollee objects can be added to the same set. Then in the Set we can sort by Name.
                 * Then we can remove duplicate Ids keeping highest version number
                 * TODO: CSVPrinter into new File for each insurance company
                 */
                System.out.println(insuranceCompanies.size());
                for(Enrollee e : eList) {
                    System.out.println(e.getInsuranceCompany());
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void mapList() {
    }
}
