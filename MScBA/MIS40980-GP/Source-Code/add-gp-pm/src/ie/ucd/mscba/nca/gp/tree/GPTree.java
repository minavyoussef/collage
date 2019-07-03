/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.gp.tree;

import ie.ucd.mscba.nca.abt.AnalyticsBaseTable;
import ie.ucd.mscba.nca.abt.AnalyticsBaseTableRow;
import ie.ucd.mscba.nca.common.DeceptionLevel;
import ie.ucd.mscba.nca.common.DetectorException;
import ie.ucd.mscba.nca.common.Utilites;

import java.util.ArrayList;

public class GPTree {
  public GPTree() {}

  @Override
  public Object clone() throws CloneNotSupportedException {
    GPTree clonedTree = new GPTree();
    clonedTree.root = (GPNode) root.clone();
    return clonedTree;
  }

  @Override
  public String toString() {
    return root.toString();
  }

  public double calculateFitness(AnalyticsBaseTable abt) {
    DetectorException.throwIfNull(abt, "abt");

    // TODO: Create confusion matrix.
    int failedClassified = 0;
    int correctClassified = 0;
    for (AnalyticsBaseTableRow observation : abt.getObservations()) {
      try {
        // Observation
        bind(root, observation);

        String labelAttributeName = abt.getLabelColumnName();
        Boolean expectedLabel = (Boolean) observation.get(labelAttributeName);
        Boolean actualLabel = (evaluate(root) == DeceptionLevel.Deceptive);

        if (actualLabel == expectedLabel) {
          ++correctClassified;
        } else {
          ++failedClassified;
        }

        unbind(root);
      } catch (DetectorException e) {
        return 0.0;
      }
    }

    return (double) correctClassified / ((double) correctClassified + (double) failedClassified);
  }

  private void bind(GPNode node, AnalyticsBaseTableRow observation) throws DetectorException {
    switch (node.getNodeType()) {
      case Terminal :
        return;

      case Internal :
        GPNodeInternal internalNode = (GPNodeInternal) node;

        internalNode.getTestPoint().bind(observation);
        bind(internalNode.getFalseBranch(), observation);
        bind(internalNode.getFalseBranch(), observation);
        return;

      default :
        throw new DetectorException("[GPTree]::[Unexpected node type during binding]");
    }
  }

  private void unbind(GPNode node) throws DetectorException {
    switch (node.getNodeType()) {
      case Terminal :
        return;

      case Internal :
        GPNodeInternal internalNode = (GPNodeInternal) node;

        internalNode.getTestPoint().unbind();
        unbind(internalNode.getFalseBranch());
        unbind(internalNode.getFalseBranch());
        return;

      default :
        throw new DetectorException("[GPTree]::[Unexpected node type during binding]");
    }
  }

  private DeceptionLevel evaluate(GPNode node) throws DetectorException {
    switch (node.getNodeType()) {
      case Terminal :
        GPNodeTerminal terminalNode = (GPNodeTerminal) node;
        return terminalNode.getDeceptionLevel();

      case Internal :
        GPNodeInternal internalNode = (GPNodeInternal) node;

        if (internalNode.getTestPoint().evaluate())
          return evaluate(internalNode.getTrueBranch());
        else
          return evaluate(internalNode.getFalseBranch());

      default :
        throw new DetectorException("[GPTree]::[Unexpected node type during evaluation]");
    }
  }

  public GPNode getRoot() {
    return root;
  }

  public void setRoot(GPNode root) {
    this.root = root;
  }

  public GPNodeInternal pickRandomInternalNode() {
    ArrayList<GPNodeInternal> internalNodesCollection = new ArrayList<>();
    extractInternalNodes(root, internalNodesCollection);

    if (internalNodesCollection.size() > 1) {
      int min = 1;
      int max = internalNodesCollection.size() - 1;
      int randInternalNodeIndex = Utilites.randomize(min, max);

      return internalNodesCollection.get(randInternalNodeIndex);
    }

    return null;
  }

  public GPNodeTerminal pickRandomTerminalNode() {
    ArrayList<GPNodeTerminal> terminalNodesCollection = new ArrayList<>();
    extractTerminalNodes(root, terminalNodesCollection);

    if (terminalNodesCollection.size() > 1) {
      int min = 1;
      int max = terminalNodesCollection.size() - 1;
      int randTerminalNodeIndex = Utilites.randomize(min, max);

      return terminalNodesCollection.get(randTerminalNodeIndex);
    }

    return null;
  }

  public int getMaxDepth() {
    return getMaxDepth(root);
  }

  private int getMaxDepth(GPNode node) {
    if (node.getNodeType() == GPNodeType.Terminal) {
      return 0;
    } else {
      GPNodeInternal internalNode = (GPNodeInternal) node;

      int lDepth = getMaxDepth(internalNode.getTrueBranch());
      int rDepth = getMaxDepth(internalNode.getFalseBranch());

      return (lDepth > rDepth) ? (lDepth + 1) : (rDepth + 1);
    }
  }

  private void extractInternalNodes(GPNode node, ArrayList<GPNodeInternal> internalNodesCollection) {
    if (node.getNodeType() == GPNodeType.Internal) {
      GPNodeInternal internalNode = (GPNodeInternal) node;
      internalNodesCollection.add(internalNode);

      extractInternalNodes(internalNode.getTrueBranch(), internalNodesCollection);
      extractInternalNodes(internalNode.getFalseBranch(), internalNodesCollection);
    }
  }

  private void extractTerminalNodes(GPNode node, ArrayList<GPNodeTerminal> terminalNodesCollection) {
    if (node.getNodeType() == GPNodeType.Terminal) {
      terminalNodesCollection.add((GPNodeTerminal) node);
    } else {
      GPNodeInternal internalNode = (GPNodeInternal) node;
      extractTerminalNodes(internalNode.getTrueBranch(), terminalNodesCollection);
      extractTerminalNodes(internalNode.getFalseBranch(), terminalNodesCollection);
    }
  }

  private GPNode root;
}
