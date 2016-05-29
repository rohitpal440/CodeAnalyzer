package octaveInterface;


OctaveEngine octave = new OctaveEngineFactory().getScriptEngine();
OctaveDouble a = new OctaveDouble(new double[] { 1, 2, 3, 4 }, 2, 2);
octave.put("a", a);
String func = "" //
        + "function res = my_func(a)\n" //
        + " res = 2 * a;\n" //
        + "endfunction\n" //
        + "";
octave.eval(func);
octave.eval("b = my_func(a);");
OctaveDouble b = octave.get(OctaveDouble.class, "b");
octave.close();

 
