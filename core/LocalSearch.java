package core;

import tool.Constraint;
import tool.Operator;

import java.util.*;

public class LocalSearch {
    int cnt = 1;
    Map<String, Integer> state = new LinkedHashMap<>();
    Map<String, List<Integer>> variables;
    List<Constraint> constraints;
    Mode mode;
    Map<String, Set<Integer>> rcMap;

    public LocalSearch(Map<String, Set<Integer>> variables, Set<Constraint> constraints, String mode) {
        this.constraints = new ArrayList<>(constraints);
        this.variables = new HashMap<>();
        for (String var : variables.keySet()) {
            List<Integer> list = new ArrayList<>(variables.get(var));
            Collections.sort(list);
            this.variables.put(var, list);
        }
        switch (mode) {
            case "none":
                this.mode = Mode.NONE;
                break;
            case "fc":
                this.mode = Mode.FORWARD_CHECKING;
                break;
            default:
                throw new RuntimeException("No implemented");
        }
    }

    public void run() {
        if (!preCheck()) throw new RuntimeException("Constraints and/or Variables Not Applicable");
        if (mode == Mode.FORWARD_CHECKING) init();
        doDFS();
    }

    private boolean preCheck() {
        if (constraints.size() == 0 || variables.size() == 0) return false;
        Set<String> keySet = variables.keySet();
        for (Constraint c : constraints)
            if (!keySet.contains(c.getVariable1()) || !keySet.contains(c.getVariable2())) return false;
        return true;
    }

    // Establish the relation between each var and its relevant constraints;
    private void init() {
        rcMap = new HashMap<>();
        for (int i = 0; i < constraints.size(); i++) {
            Constraint c = constraints.get(i);
            String v1 = c.getVariable1();
            String v2 = c.getVariable2();
            if (!rcMap.containsKey(v1)) rcMap.put(v1, new HashSet<>());
            if (!rcMap.containsKey(v2)) rcMap.put(v2, new HashSet<>());
            rcMap.get(v1).add(i);
            rcMap.get(v2).add(i);
        }
    }

    private boolean doDFS() {
        if (state.size() == variables.keySet().size()) {
            System.out.println(cnt + ". " + state.toString().substring(1, state.toString().length() - 1) + " Solution");
            return true;
        }
        String curr = pickVariable();
        prioritizeValue(curr);
        for (int v : variables.get(curr)) {
            state.put(curr, v);
            if (mode == Mode.FORWARD_CHECKING) { // FC toggle
                if (!doFC(curr, v)){
                    System.out.println(cnt++ + ". " + state.toString().substring(1, state.toString().length() - 1) + " Failure");
                    state.remove(curr);
                    continue;
                }
            }
            if (validate()) {
                if (doDFS()) return true;
            } else
                System.out.println(cnt++ + ". " + state.toString().substring(1, state.toString().length() - 1) + " Failure");
            state.remove(curr);
        }
        return false;
    }

    private boolean doFC(String curr, int val) {
        Set<Integer> currSet = rcMap.get(curr);
        if (currSet == null) return true;
        for (String var : variables.keySet()) {
            if (state.containsKey(var)) continue;
            Set<Integer> varSet = rcMap.get(var);
            if (varSet == null) continue;
            for (int i = 0; i < constraints.size(); i++) {
                if (!varSet.contains(i) || !currSet.contains(i)) continue;
                List<Integer> newDomain = new ArrayList<>();
                Constraint c = constraints.get(i);
                Operator op = c.getOperator();
                if (op == Operator.EQUALITY) {
                    for (int v : variables.get(var)) if (v == val) newDomain.add(v);
                }
                if (op == Operator.INEQUALITY) {
                    for (int v : variables.get(var)) if (v != val) newDomain.add(v);
                }
                if ((op == Operator.GREATER_THAN && var.equals(c.getVariable1())) || (op == Operator.LESS_THAN && var.equals(c.getVariable2()))) {
                    for (int v : variables.get(var)) if (v > val) newDomain.add(v);
                }
                if ((op == Operator.LESS_THAN && var.equals(c.getVariable1())) || (op == Operator.GREATER_THAN && var.equals(c.getVariable2()))) {
                    for (int v : variables.get(var)) if (v < val) newDomain.add(v);
                }
                if (newDomain.size() == 0) return false;
                variables.put(var, newDomain);
            }
        }
        return true;
    }


    private boolean validate() {
        for (Constraint c : constraints) {
            String v1 = c.getVariable1();
            String v2 = c.getVariable2();
            if (!state.containsKey(v1) || !state.containsKey(v2)) continue;
            int val1 = state.get(v1);
            int val2 = state.get(v2);
            Operator op = c.getOperator();
            if (op == Operator.EQUALITY && val1 != val2) return false;
            if (op == Operator.INEQUALITY && val1 == val2) return false;
            if (op == Operator.GREATER_THAN && val1 <= val2) return false;
            if (op == Operator.LESS_THAN && val1 >= val2) return false;
        }
        return true;
    }

    private String pickVariable() {
        // Step1: Most Constrained
        List<String> mostConstrained = new ArrayList<>();
        int mc = Integer.MAX_VALUE;
        for (String var : variables.keySet()) {
            if (state.containsKey(var)) continue;
            mc = Math.min(mc, variables.get(var).size());
        }
        for (String var : variables.keySet()) {
            if (state.containsKey(var)) continue;
            if (variables.get(var).size() == mc) mostConstrained.add(var);
        }

        if (mostConstrained.size() == 1) return mostConstrained.get((0));

        // Step 2: Tie occurs, using most constraining
        List<String> mostConstraining = new ArrayList<>();
        int mci = 0;

        for (String var : mostConstrained) {
            int curr = 0;
            for (Constraint c : constraints) {
                if ((var.equals(c.getVariable1()) && !state.containsKey(c.getVariable2())) || (var.equals(c.getVariable2()) && !state.containsKey(c.getVariable1())))
                    curr++;
            }
            mci = Math.max(mci, curr);
        }

        for (String var : mostConstrained) {
            int curr = 0;
            for (Constraint c : constraints) {
                if ((var.equals(c.getVariable1()) && !state.containsKey(c.getVariable2())) || (var.equals(c.getVariable2()) && !state.containsKey(c.getVariable1())))
                    curr++;
            }
            if (curr == mci) mostConstraining.add(var);
        }

        if (mostConstraining.size() == 1) return mostConstraining.get((0));

        // Step 3: Tie Still occurs, using alphabetical order
        String str = mostConstraining.get(0);
        for (int i = 1; i < mostConstraining.size(); i++) {
            String candidate = mostConstraining.get(i);
            if (candidate.compareTo(str) < 0) str = candidate;
        }
        return str;
    }

    private void prioritizeValue(String curr) {
        List<Integer> domain = variables.get(curr);
        Map<Integer, Integer> evaluation = new HashMap<>();
        for (int i = 0; i < domain.size(); i++) {
            int val = domain.get(i);
            int score = 0;
            for (String var : variables.keySet()) {
                if (state.containsKey(var) || var.equals(curr)) continue; // Only on Remaining
                score += evaluate(curr, val, var);
            }
            evaluation.put(val, score);
        }
        domain.sort((Integer v1, Integer v2) -> {
            int e1 = evaluation.get(v1);
            int e2 = evaluation.get(v2);
            return e1 != e2 ? e2 - e1 : v1 - v2; // Use numerical if ties occurs
        });
    }

    private int evaluate(String curr, int val, String var) {
        List<Integer> domain = variables.get(var);
        for (Constraint c : constraints) {
            if (domain.size() == 0) return 0;
            if ((curr.equals(c.getVariable1()) && var.equals(c.getVariable2())) ||
                    (curr.equals(c.getVariable2()) && var.equals(c.getVariable1()))) {
                Operator op = c.getOperator();
                switch (op) {
                    case EQUALITY:
                        boolean exist = false;
                        for (int v : domain)
                            if (v == val) {
                                exist = true;
                                break;
                            }
                        if (!exist) return 0;
                        domain = new ArrayList<>();
                        domain.add(val);
                        break;
                    case INEQUALITY:
                        domain.remove(val);
                        break;
                    // > / <
                    default:
                        String v1 = c.getVariable1();
                        String v2 = c.getVariable2();
                        int L = 0, R = domain.size() - 1;
                        if ((var.equals(v1) && op == Operator.GREATER_THAN) || (var.equals(v2) && op == Operator.LESS_THAN)) {
                            while (L + 1 < R) {
                                int mid = L + ((R - L) >> 1);
                                int cur = domain.get(mid);
                                if (cur < val) {
                                    L = mid;
                                } else {
                                    R = mid;
                                }
                            }
                            if (domain.get(R) <= val) return 0;
                            List<Integer> l = new ArrayList<>();
                            for (int i = R; i < domain.size(); i++) l.add(domain.get(i));
                            domain = l;
                        } else {
                            while (L + 1 < R) {
                                int mid = L + ((R - L) >> 1);
                                int cur = domain.get(mid);
                                if (cur < val) {
                                    L = mid;
                                } else {
                                    R = mid;
                                }
                            }
                            if (domain.get(L) >= val) return 0;
                            List<Integer> l = new ArrayList<>();
                            for (int i = 0; i <= L; i++) l.add(domain.get(i));
                            domain = l;
                        }
                }
            }
        }
        return domain.size();
    }

    enum Mode {
        NONE, FORWARD_CHECKING
    }
}


