import core.LocalSearch;
import tool.Parser;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new RuntimeException("Args format: <path_to_var_file> <path_to_con_file> <none|fc>.");
        }
        String dotVarPath = args[0];
        String dotConPath = args[1];
        String mode = args[2];
        Parser parser = new Parser();
        LocalSearch ls = new LocalSearch(parser.parseDotVar(dotVarPath), parser.parseDotCon(dotConPath), mode);

        ls.run();
    }
}
