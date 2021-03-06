package com.availity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.*;


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
                HashMap<String, SortedSet<Enrollee>> enrolleeToCompanyMap = new HashMap<String, SortedSet<Enrollee>>();

                for(CSVRecord r : records) {
                    // Create new Enrollee, capture fields
                    Enrollee e = new Enrollee();
                    e.setUserId(r.get(0)); // "User Id" not working, index 0 is equivalent
                    e.setFirstName(r.get("First Name"));
                    e.setLastName(r.get("Last Name"));
                    e.setVersion(r.get("Version"));
                    e.setInsuranceCompany(r.get("Insurance Company"));

                    /* If key doesn't already have a value, compute function will run and add a new Enrollee
                     *  HashSet for that insurance company. Then we add the current enrollee to that set.
                     */
                    enrolleeToCompanyMap.computeIfAbsent(e.getInsuranceCompany(), k -> new TreeSet<Enrollee>());
                    addEnrolleeToCompanyMap(enrolleeToCompanyMap.get(e.getInsuranceCompany()), e);
                }

                // Create the separate output files.
                for(String company : enrolleeToCompanyMap.keySet()) {
                    try {
                        createCompanyFile(company, enrolleeToCompanyMap.get(company));
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void addEnrolleeToCompanyMap(SortedSet<Enrollee> currentEnrolleeSet, Enrollee e) {
        /*
         * Before adding the enrollee to the company map, this function will check to see if that company's Set of
         * employees already contains this enrollee's User Id.
         * If it does, it compares versions and keeps the object with the higher version, removing the other.
         * If it does not, it adds the current enrollee object.
         * We shouldn't have to worry about a case where there are already multiple identical User Ids in the set
         * because we are calling this method every time.
         */
        List<Enrollee> toRemove = new ArrayList<>();
        List<Enrollee> toAdd = new ArrayList<>();

        if(!currentEnrolleeSet.isEmpty()) {
            currentEnrolleeSet.forEach(currentEnrollee -> {
                if (currentEnrollee.getUserId().hashCode() == e.getUserId().hashCode()) {
                    if (e.getVersion() > currentEnrollee.getVersion()) {
                        toAdd.add(e);
                        toRemove.add(currentEnrollee);
                    }
                }
                else {
                    toAdd.add(e);
                }
            });
            currentEnrolleeSet.removeAll(toRemove);
            currentEnrolleeSet.addAll(toAdd);
        }
        else {
            currentEnrolleeSet.add(e); // Set was empty, clear to add enrollee.
        }
    }

    private static void createCompanyFile(String company, SortedSet<Enrollee> set) throws IOException {
        try {
            String path = "TestFiles\\out\\CSV\\" + company + ".csv"; // TODO: Use relative path instead of hard coded.
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(path));
            StringBuilder csvOut = new StringBuilder("User Id,First Name,Last Name,Version,Insurance Company");

            for(Enrollee en : set) {
                csvOut.append("\n")
                        .append(en.getUserId()).append(',')
                        .append(en.getFirstName()).append(',')
                        .append(en.getLastName()).append(',')
                        .append(en.getVersion()).append(',')
                        .append(en.getInsuranceCompany()
                );
            }
            output.write(csvOut.toString().getBytes());
            output.flush();
            output.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
