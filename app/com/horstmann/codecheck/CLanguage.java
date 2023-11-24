package com.horstmann.codecheck;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CLanguage implements Language {

    @Override
    public String getExtension() {
        return "c";
    }
    
    @Override
    public boolean isSource(Path p) {
        String name = p.toString();
        return name.endsWith(".c") || name.endsWith(".h") || name.endsWith(".s") || name.endsWith(".S");
    }
    
    private static Pattern MAIN_PATTERN = Pattern.compile("\\s*((int|void)\\s+)?main\\s*\\([^)]*\\)\\s*(\\{\\s*)?");
    @Override public Pattern mainPattern() { return MAIN_PATTERN; }

    private static Pattern VARIABLE_PATTERN = Pattern.compile(".*\\S\\s+(?<name>[A-Za-z][A-Za-z0-9]*)(\\s*[*\\[\\]]+)?\\s*=\\s*(?<rhs>[^;]+);.*");
    @Override public Pattern variableDeclPattern() { return VARIABLE_PATTERN; }
       
    private static Pattern ERROR_PATTERN = Pattern.compile(".+/(?<file>[^/]+\\.cpp):(?<line>[0-9]+):(?<col>[0-9]+): error: (?<msg>.+)");
    @Override public Pattern errorPattern() { return ERROR_PATTERN; }    

    @Override
    public String functionName(String functionSignature) {
        // Regular expression to extract the function type
        // This is a simplistic pattern and might need refinement
        Pattern pattern = Pattern.compile("([\\w\\*]+)\\s+\\w+\\(");
        Matcher matcher = pattern.matcher(functionSignature);
        if (matcher.find()) {
            return matcher.group(1).trim().replace("static ", "").replace("const ", "").replace("extern ", "");
        }
        return null;
    }

    // TODO: Implement this
    @Override
    public Map<Path, String> writeTester(Path file, String contents, List<Calls.Call> calls, ResourceLoader resourceLoader) {
        
        String moduleName = moduleOf(file);
        List<String> lines = new ArrayList<>();
        //List<String> lines = Util.readLines(sourceDir.resolve(file));
        
        for (Calls.Call call : calls) {
            String printFunction = mapTypeToPrintFunction(call.type);
            lines.add("        codecheck::" + printFunction + "(" + call.name + "(" + call.args + "));");
        }

        Map<Path, String> paths = new HashMap<>();
        paths.put(pathOf(moduleName + "CodeCheck"), "");
        return paths;
    }

    private String mapTypeToPrintFunction(String type) {
        switch (type) {
            case "int":
                return "print_int";
            case "double":
                return "print_double";
            case "char*":
                return "print_char_pointer";
            case "bool":
                return "print_bool";
            case "char":
                return "print_char";
            default:
                return "print_unsupported";
        }
    }
}