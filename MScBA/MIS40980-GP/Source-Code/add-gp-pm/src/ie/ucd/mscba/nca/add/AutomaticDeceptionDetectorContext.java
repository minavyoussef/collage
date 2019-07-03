/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.add;

import ie.ucd.mscba.nca.ds.DatasetBase;

public class AutomaticDeceptionDetectorContext {
  public DatasetBase getDataset() {
    return dataset;
  }

  public void setDataset(DatasetBase dataset) {
    this.dataset = dataset;
  }

  public long getSeed() {
    return seed;
  }

  public void setSeed(long seed) {
    this.seed = seed;
  }

  private DatasetBase dataset = null;
  private long seed = 28031985; // My birthday :)
}
