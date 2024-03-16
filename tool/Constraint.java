package tool;

public class Constraint {

    private String variable1;
    private String variable2;
    private Operator operator;

    // exp: A > B
    public Constraint(String line) {
        String[] s = line.split(" ");
        String v1 = s[0];
        String v2 = s[2];
        Operator op;
        switch (s[1]) {
            case "=":
                op = Operator.EQUALITY;
                break;
            case "!=":
                op = Operator.INEQUALITY;
                break;
            case ">":
                op = Operator.GREATER_THAN;
                break;
            case "<":
                op = Operator.LESS_THAN;
                break;
            default:
                throw new RuntimeException("The Operator in constraint is illegal: " + line);
        }
        this.operator = op;
        this.variable1 = v1;
        this.variable2 = v2;

//        // For simplify, paraphrase A < B to B > A
//        if (operator == Operator.LESS_THAN) {
//            operator = Operator.GREATER_THAN;
//            String temp = variable1;
//            variable1 = variable2;
//            variable2 = temp;
//        }
    }

    public String getVariable1() {
        return variable1;
    }

    public String getVariable2() {
        return variable2;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public boolean equals(Object obj) {
        Constraint c = (Constraint) obj;
        return this.variable1.equals(c.variable1) && this.variable2.equals(c.variable2) && this.operator.equals(c.operator);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.variable1);
        String oper = "";
        switch (this.operator) {
            case EQUALITY:
                oper = "=";
                break;
            case INEQUALITY:
                oper = "!=";
                break;
            case GREATER_THAN:
                oper = ">";
                break;
            case LESS_THAN:
                oper = "<";
        }
        sb.append(oper);
        sb.append(this.variable2);
        return sb.toString();
    }
}

