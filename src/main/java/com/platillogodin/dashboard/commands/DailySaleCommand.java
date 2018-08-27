package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySaleCommand {
    List<String> labels = new ArrayList<>();
    Map<String, List<Integer>> data = new HashMap();
}