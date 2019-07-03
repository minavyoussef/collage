/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp.common;

import ie.ucd.mscba.nca.gp.crossover.Crossable;
import ie.ucd.mscba.nca.gp.crossover.CrossoverTypes;
import ie.ucd.mscba.nca.gp.crossover.StandardCrossover;
import ie.ucd.mscba.nca.gp.mutation.Mutation;
import ie.ucd.mscba.nca.gp.mutation.Mutationable;
import ie.ucd.mscba.nca.gp.selection.Selectable;
import ie.ucd.mscba.nca.gp.selection.SelectionTypes;
import ie.ucd.mscba.nca.gp.selection.TournamentSelection;

import java.util.Hashtable;

public class ComponentFactory {
  public static synchronized Selectable getSelectionOperation(SelectionTypes type) {
    return selectionOperations.get(type);
  }

  public static synchronized Crossable getCrossoverOperation(CrossoverTypes type) {
    return crossoverOperations.get(type);
  }

  public static Mutationable getMutationOperation() {
    return mutationOperation;
  }

  private static Hashtable<SelectionTypes, Selectable> selectionOperations;
  private static Hashtable<CrossoverTypes, Crossable> crossoverOperations;
  private static Mutationable mutationOperation;

  static {
    selectionOperations = new Hashtable<>();
    selectionOperations.put(SelectionTypes.Tournament, new TournamentSelection());

    crossoverOperations = new Hashtable<>();
    crossoverOperations.put(CrossoverTypes.Standard, new StandardCrossover());

    mutationOperation = new Mutation();
  }
}
