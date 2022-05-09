package com.googlecode.paradox.procedures;

import java.util.ArrayList;

import com.googlecode.paradox.procedures.math.Averange;
import com.googlecode.paradox.procedures.math.Count;
import com.googlecode.paradox.procedures.math.Max;
import com.googlecode.paradox.procedures.math.Min;
import com.googlecode.paradox.procedures.math.Sum;

/**
 *
 * @author 72330554168
 */
public class ProcedureAS {

    private static final ProcedureAS instance = new ProcedureAS();

    public static ProcedureAS getInstance() {
        return instance;
    }


    private ArrayList<CallableProcedure> procedures = new ArrayList<CallableProcedure>();

    private ProcedureAS() {
        register(new Averange());
        register(new Count());
        register(new Max());
        register(new Min());
        register(new Sum());
    }

    public void register(final CallableProcedure procedure) {
        procedures.add(procedure);
    }

    public ArrayList<CallableProcedure> list() {
        return procedures;
    }

    public CallableProcedure get(final String name) {
        for (final CallableProcedure procedure : procedures) {
            if (procedure.getName().equalsIgnoreCase(name)) {
                return procedure;
            }
        }
        return null;
    }
}
