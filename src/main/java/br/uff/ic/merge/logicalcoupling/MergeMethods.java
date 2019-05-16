package br.uff.ic.merge.logicalcoupling;

//import br.uff.ic.gems.tipmerge.util.Auxiliary;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import br.uff.ic.gems.tipmerge.model.Merge;

/**
*
* @author Cristiane
*/
public class MergeMethods extends Merge {
  
  private List<EditedMethod> methodsOnBranchOne;
  private List<EditedMethod> methodsOnBranchTwo;
  private Set<EditedMethod> methodsOnBothBranch; //retirar
  private Set<EditedMethod> methodsOnPreviousHistory;

  public MergeMethods(String hashOfMerge, File pathToRepository) {
      super(hashOfMerge, pathToRepository);
  }

  public List<EditedMethod> getMethodsOnBranchOne() {
      return methodsOnBranchOne;
  }

  public void setMethodsOnBranchOne(List<EditedMethod> methodsOnBranchOne) {
      this.methodsOnBranchOne = methodsOnBranchOne;
      if (this.methodsOnBranchTwo != null) {
          this.setMethodsOnPreviousHistory();
      }
  }

  public List<EditedMethod> getMethodsOnBranchTwo() {
      return methodsOnBranchTwo;
  }

  public void setMethodsOnBranchTwo(List<EditedMethod> methodsOnBranchTwo) {
      this.methodsOnBranchTwo = methodsOnBranchTwo;
      if (this.methodsOnBranchOne != null) {
          this.setMethodsOnPreviousHistory();
      }
  }

  /**
   * @return the methodaOnPreviousHistory
   */
  public Set<EditedMethod> getMethodsOnPreviousHistory() {
      return methodsOnPreviousHistory;
  }

  private void setMethodsOnPreviousHistory() {
      if ((this.methodsOnBranchOne != null) && (this.methodsOnBranchTwo != null)) {
          Set<EditedMethod> methodsOnMerge = new HashSet<EditedMethod>();
          this.getMethodsOnBranchOne().stream().forEach((method) -> {
              methodsOnMerge.add(new EditedMethod(method.getMethodName()));
          });
          this.getMethodsOnBranchTwo().stream().forEach((method) -> {
              methodsOnMerge.add(new EditedMethod(method.getMethodName()));
          });
          this.methodsOnPreviousHistory = methodsOnMerge;
      }
  }

  public void setMethodsOnPreviousHistory(Set<EditedMethod> methods) {
      this.methodsOnPreviousHistory = methods;
  }

  /**
   * @return the methodsOnBothBranch
   */
  public Set<EditedMethod> getMethodsOnBothBranch() {
      Set<EditedMethod> methods = new HashSet<EditedMethod>();
      if (methodsOnBothBranch == null) {
          for (EditedMethod emethod1 : this.getMethodsOnBranchOne()) {
              for (EditedMethod emethod2 : this.getMethodsOnBranchTwo()) {
                  if (emethod1.equals(emethod2)) {
                      methods.add(emethod1);
                  }
              }
          }
          methodsOnBothBranch = methods;
      }
      return methodsOnBothBranch;
  }

  public List<EditedMethod> getMethodsOnBothBranch2() {
      List<EditedMethod> methods = new ArrayList<EditedMethod>();
      for (EditedMethod emethod1 : this.getMethodsOnBranchOne()) {
          for (EditedMethod emethod2 : this.getMethodsOnBranchTwo()) {
              if (emethod1.equals(emethod2)) {
                  methods.add(emethod1);
                  methods.add(emethod2);
              }
          }
      }
      return methods;
  }
  
  /**
   * @param methodsOnBothBranch the methodsOnBothBranch to set
   */
  public void setMethodsOnBothBranch(Set<EditedMethod> methodsOnBothBranch) {
      this.methodsOnBothBranch = methodsOnBothBranch;
  }

}
