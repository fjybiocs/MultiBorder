package com.mcfuntime.MultiBorder;

import java.util.HashMap;
import java.util.Map;

public class WorldBorders {
    final private Map<String ,Border> borders = new HashMap<String, Border>();

    public void addBorder(String name, Border border){
        this.borders.put(name, border);
    }

    public Border getBorder(String name){
        return borders.get(name);
    }

    public Map<String ,Border> getBorder(){
        return borders;
    }
}
