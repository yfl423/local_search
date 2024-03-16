package tool;

import java.io.File;
import java.util.*;

public class Parser {
    public Map<String, Set<Integer>> parseDotVar(String dotVarPath) {
        File file = new File(dotVarPath);
        Map<String, Set<Integer>> variables = new HashMap<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                doDotVarParse(line, variables);
            }
        } catch (Exception e) {
            System.err.println("File is failed to parse: " + e.getMessage());
        }
        if (scanner != null) scanner.close();
        return variables;
    }

    public Set<Constraint> parseDotCon(String dotConPath) {
        File file = new File(dotConPath);
        Set<Constraint> constraints = new HashSet<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) constraints.add(new Constraint(scanner.nextLine()));
        } catch (Exception e) {
            System.err.println("File is failed to parse: " + e.getMessage());
        }
        if (scanner != null) scanner.close();
        return constraints;
    }

    private void doDotVarParse(String line, Map<String, Set<Integer>> variables) {
        // exp: A: 1 2 3 4 5
        String[] split = line.split(": ");
        String v = split[0];
        if (!variables.containsKey(v)) variables.put(v, new HashSet<>());
        Set<Integer> domain = variables.get(v);
        String[] vStrs = split[1].split(" ");
        for (String vStr : vStrs) domain.add(Integer.valueOf(vStr));
    }
}
