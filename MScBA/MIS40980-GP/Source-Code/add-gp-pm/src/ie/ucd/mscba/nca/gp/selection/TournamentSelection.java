/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp.selection;

import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.OperationResult;
import ie.ucd.mscba.nca.common.Utilites;
import ie.ucd.mscba.nca.gp.Chromosome;
import ie.ucd.mscba.nca.gp.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class TournamentSelection implements Selectable {
  @Override
  public OperationResult select(Population population) {
    DetectorException.throwIfNull(population, "population");

    Chromosome parentX = getRandomParent(population, RANDOM_SAMPLE_PERCENTAGE_COUNT);
    Chromosome parentY = getRandomParent(population, RANDOM_SAMPLE_PERCENTAGE_COUNT);
    Chromosome[] parents = new Chromosome[]{parentX, parentY};

    return new OperationResult(parents);
  }

  private Chromosome getRandomParent(Population population, double randomSamplePercentageCount) {
    Hashtable<UUID, Double> randomSubset = getRandomSelectionSubset(population, randomSamplePercentageCount);
    List<Map.Entry<UUID, Double>> sortedrandomSubset = sortRandomSample(randomSubset);

    Entry<UUID, Double> mostFit = sortedrandomSubset.get(0);
    return population.get(mostFit.getKey());
  }

  private Hashtable<UUID, Double> getRandomSelectionSubset(Population population, double percentage) {
    Hashtable<UUID, Double> populationCache = population.getFitnessCache();
    UUID[] populationCacheArray = populationCache.keySet().toArray(new UUID[populationCache.keySet().size()]);

    int minRandomRange = 0;
    int maxRandomRange = populationCacheArray.length - 1;

    Hashtable<UUID, Double> randomSelectedSubset = new Hashtable<>();
    int selectedCount = (int) (population.getSize() * percentage);
    while (selectedCount-- >= 0) {
      UUID randomChromosomeUUID = populationCacheArray[Utilites.randomize(minRandomRange, maxRandomRange)];
      randomSelectedSubset.put(randomChromosomeUUID, populationCache.get(randomChromosomeUUID));
    }
    return randomSelectedSubset;
  }

  public static List<Map.Entry<UUID, Double>> sortRandomSample(Map<UUID, Double> map) {
    List<Entry<UUID, Double>> entryList = new ArrayList<Entry<UUID, Double>>(map.entrySet());
    Collections.sort(entryList, new Comparator<Entry<UUID, Double>>() {
      public int compare(Entry<UUID, Double> first, Entry<UUID, Double> second) {
        return -(first.getValue().compareTo(second.getValue()));
      }
    });

    return entryList;
  }

  private static final double RANDOM_SAMPLE_PERCENTAGE_COUNT = 0.10; // % [0.01 - 0.99]
}