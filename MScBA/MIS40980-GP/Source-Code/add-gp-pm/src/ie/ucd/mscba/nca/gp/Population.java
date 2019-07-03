/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp;

import ie.ucd.mscba.nca.common.DetectorException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public class Population {
  public Population() {
    chromosomes = new Hashtable<>();
    fitnessCache = new Hashtable<>();
  }

  public static Population generateRandom(Context context) {
    DetectorException.throwIfNull(context, "context");
    int populationSize = context.getPopulationSize();

    Population population = new Population();
    while (populationSize-- != 0) {
      Chromosome randomChromosome = Chromosome.createRandom(context);
      population.chromosomes.put(randomChromosome.getId(), randomChromosome);
    }
    return population;
  }

  @Override
  public String toString() {
    ArrayList<String> values = new ArrayList<>();
    for (double v : fitnessCache.values()) {
      values.add(String.format("%.2f%%", v));
    }
    return String.join(", ", values);
  }
  public void add(Chromosome chromosome) {
    DetectorException.throwIfNull(chromosome, "chromosome");

    chromosomes.put(chromosome.getId(), chromosome);
  }

  public Hashtable<UUID, Double> calculateFitness(Context context) {
    DetectorException.throwIfNull(context, "context");

    fitnessCache.clear();
    for (Chromosome chromosomeIterator : chromosomes.values()) {
      UUID id = chromosomeIterator.getId();
      double fitnessValue = chromosomeIterator.calculateFitness(context);
      //Logger.Log(LogLevel.Trace, "Chromosoe [%s] / Fitness %.2f%%", id.toString(), fitnessValue * 100);
      fitnessCache.put(id, fitnessValue);
    }
    return fitnessCache;
  }

  public Hashtable<UUID, Double> getFitnessCache() {
    return fitnessCache;
  }

  public int getSize() {
    return chromosomes.size();
  }

  public Chromosome get(UUID chromosomeUUID) {
    return chromosomes.get(chromosomeUUID);
  }

  public double getAvaragePopulationFitness() {
    if (fitnessCache == null) {
      return 0.0;
    }

    return fitnessCache.values().stream().mapToDouble(a -> a).average().getAsDouble();
  }

  public double getMaxFit() {
    if (fitnessCache == null) {
      return 0.0;
    }

    return fitnessCache.values().stream().mapToDouble(a -> a).max().getAsDouble();
  }

  public double getMinFit() {
    if (fitnessCache == null) {
      return 0.0;
    }

    return fitnessCache.values().stream().mapToDouble(a -> a).min().getAsDouble();
  }

  public Double[] getFitnessCacheVector() {
    Double vector[] = new Double[fitnessCache.values().size()];
    vector = fitnessCache.values().toArray(vector);
    return vector;
  }

  public double calcuateDistance(Population population) {
    Double[] v1 = getFitnessCacheVector();
    Double[] v2 = population.getFitnessCacheVector();

    if (v1.length == v2.length) {
      Double sum = 0D;
      for (int i = 0; i < v1.length; i++) {
        sum = sum + (v2[i] - v1[i]) * (v2[i] - v1[i]);
      }
      return sum;
    } else {
      return 0.0;
    }
  }

  private Hashtable<UUID, Chromosome> chromosomes;
  private Hashtable<UUID, Double> fitnessCache;
}
