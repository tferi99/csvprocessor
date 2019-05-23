package org.ftoth.cvsproc;

import org.apache.log4j.Logger;
import org.ftoth.cvsproc.general.CsvGroupProcessor;

public class CsvProcessor
{
    private static Logger log = Logger.getLogger(CsvProcessor.class);

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
        CsvProcessor app = new CsvProcessor(mode, args[1]);

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
        System.err.println("Usage: " + CsvProcessor.class.getSimpleName() + " <mode> <input CSV file>");
        System.exit(1);
    }

    private CsvProcessor(Mode mode, String inputFile)
    {
        this.mode = mode;
        this.inputFile = inputFile;

        try {
            CsvGroupProcessor p = null;
            switch (mode) {
                case GRP2:
                    p = new org.ftoth.cvsproc.grp2.CsvGroupProcessorImpl(inputFile);
            }
            switch (mode) {
                case GRP3:
                    p = new org.ftoth.cvsproc.grp3.CsvGroupProcessorImpl(inputFile);
            }
            p.process();
            //System.out.println(p.dump());
        }
        catch (Exception e) {
            log.error("Error during processing " + inputFile, e);
        }
    }
}
