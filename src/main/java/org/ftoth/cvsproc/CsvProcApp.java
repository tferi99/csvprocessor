package org.ftoth.cvsproc;

import org.apache.log4j.Logger;
import org.ftoth.cvsproc.grp2.CsvGroupProcessorImpl;
import org.ftoth.cvsproc.utils.CsvGroupProcessor;

public class CsvProcApp
{
    private static Logger log = Logger.getLogger(CsvProcApp.class);

    public static enum Mode {
        GRP2, GRP3
    }

    private Mode mode;
    private String inputFile;

    public static void main(String[] args)
    {

        if (args.length != 2) {
            usage();
        }
        Mode mode = validateMode(args[0]);
        CsvProcApp app = new CsvProcApp(mode, args[1]);

    }

    private static Mode validateMode(String mode) {
        try {
            return Mode.valueOf(mode);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(mode + " : bas mode");
        }
    }

    private static void usage() {
        System.err.println("Usage: " + CsvProcApp.class.getSimpleName() + " <mode> <input CSV file>");
        System.exit(1);
    }

    private CsvProcApp(Mode mode, String inputFile)
    {
        this.mode = mode;
        this.inputFile = inputFile;

        try {
            CsvGroupProcessor p = null;
            switch (mode) {
                case GRP2:
                    p = new CsvGroupProcessorImpl(inputFile);
            }
            p.process();
            //System.out.println(p.dump());
        }
        catch (Exception e) {
            log.error("Error during processing " + inputFile, e);
        }
    }
}
