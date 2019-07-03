package ie.ucd.mscba.nca.gp.tree;

public class GPNodeInternal extends GPNode {
  public GPNodeInternal() {
    super(GPNodeType.Internal);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    GPNodeInternal clonedInternal = new GPNodeInternal();
    clonedInternal.testPoint = testPoint.cloneTestabilityPoint();
    clonedInternal.trueBranch = (GPNode) trueBranch.clone();
    clonedInternal.falseBranch = (GPNode) falseBranch.clone();

    return clonedInternal;
  }

  @Override
  public String toString() {
    return "(" + testPoint.toString() + " ? " + trueBranch.toString() + " : " + falseBranch.toString() + ")";
  }

  public GPTestableCondition getTestPoint() {
    return testPoint;
  }

  public void setTestPoint(GPTestableCondition testPoint) {
    this.testPoint = testPoint;
  }

  public GPNode getTrueBranch() {
    return trueBranch;
  }

  public void setTrueBranch(GPNode trueBranch) {
    this.trueBranch = trueBranch;
  }

  public GPNode getFalseBranch() {
    return falseBranch;
  }

  public void setFalseBranch(GPNode falseBranch) {
    this.falseBranch = falseBranch;
  }

  private GPTestableCondition testPoint;
  private GPNode trueBranch;
  private GPNode falseBranch;
}
