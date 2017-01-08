package pojos;

import lombok.Data;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.List;

/**
 * Created by solvie on 2017-01-09.
 */
@Data

public class SbAssignment {
    private int assignmentNum;
    private Language language;
    private TestFormat testFormat;
    private DualHashBidiMap<String, String> outputTests;
    private String description;


    public SbAssignment(){}

    public enum Language{
        C, JAVA;
    }

    public enum TestFormat{
        UNIT_TEST, OUTPUT;
    }

    public static SbAssignment findAsst(List<SbAssignment> assts, int asstnum){
        for (SbAssignment asst: assts)
            if (asst.getAssignmentNum()==asstnum)
                return asst;
        return null;
    }
}
