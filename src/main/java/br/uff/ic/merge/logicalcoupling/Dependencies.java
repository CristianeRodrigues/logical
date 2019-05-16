package br.uff.ic.merge.logicalcoupling;

	import arch.Cell;
	import arch.IMatrix2D;
	import domain.Dominoes;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.List;
	import java.util.Map;
	import java.util.Set;
	//import br.uff.ic.gems.tipmerge.model.EditedFile;

	/**
	 *
	 * @author J, Cristiane
	 */
	public class Dependencies {

	    private Dominoes dominoes;

	    public Dependencies(Dominoes dominoes) {
	        this.dominoes = dominoes;
	    }

	    /**
	     * @return the dominoes
	     */
	    public Dominoes getDominoes() {
	        return dominoes;
	    }

	    /**
	     * @param dominoes the dominoes to set
	     */
	    public void setDominoes(Dominoes dominoes) {
	        this.dominoes = dominoes;
	    }

	    /**
	     * Returns a list of antecedentFiles that have some dependence with the list
	     * of filesEdited antecedentFiles, following the defined threshold.
	     *
	     * @param antecedentFiles @param threshold @param consequentFiles @param
	     * excepiontFiles
	     * @return fileDependencies
	     */
	    public Map<EditedMethod, Set<EditedMethod>> getDependenciesAcrossBranches(
	            List<EditedMethod> antecedentMethods,
	            List<EditedMethod> consequentMethods, double threshold) {

	        Map<EditedMethod, Set<EditedMethod>> dependenciesAcrossBranches = new HashMap<EditedMethod, Set<EditedMethod>>();

	        IMatrix2D matrix = getDominoes().getMat();

	        int rows = matrix.getMatrixDescriptor().getNumRows();
	        int cols = matrix.getMatrixDescriptor().getNumCols();

	        List<Cell> cells = matrix.getNonZeroData();

	        for (int i = 0; i < cols; i++) {

	            EditedMethod antecendentTmp = new EditedMethod(matrix.getMatrixDescriptor().getColumnAt(i));
	            Set<EditedMethod> methodDependencies = new HashSet<EditedMethod>();
	            boolean hasDependencies = false;

	            int indexOf = antecedentMethods.indexOf(antecendentTmp);
	            if (indexOf > -1) {

	                antecendentTmp = antecedentMethods.get(indexOf);

	                for (int j = 0; j < rows; j++) {
	                    EditedMethod consequentTmp = new EditedMethod(matrix.getMatrixDescriptor().getRowAt(j));
	                    indexOf = consequentMethods.indexOf(consequentTmp);
	                    if ((i != j) && (indexOf > -1)) {
	                        for (Cell c : cells) {
	                        	//&& (c.value < (threshold+1))
	                            if ((c.value >= threshold)  && (c.row == j) && (c.col == i)) {
	                                methodDependencies.add(consequentMethods.get(indexOf));
	                                hasDependencies = true;
	                            }
	                        }
	                    }
	                }
	            }
	            if (hasDependencies) {
	                dependenciesAcrossBranches.put(antecendentTmp, methodDependencies);
	            }
	        }
	        return dependenciesAcrossBranches;
	    }

	    public Map<EditedMethod, Set<EditedMethod>> getMethodsDependencies(Set<EditedMethod> allMethodsOnMerge, double threshold) {

	        Map<EditedMethod, Set<EditedMethod>> dependenciesList = new HashMap<EditedMethod, Set<EditedMethod>>();
	        List<EditedMethod> branchMethods = new ArrayList<EditedMethod>(allMethodsOnMerge);

	        IMatrix2D matrix = getDominoes().getMat();

	        int rows = matrix.getMatrixDescriptor().getNumRows();
	        int cols = matrix.getMatrixDescriptor().getNumCols();

	        List<Cell> cells = matrix.getNonZeroData();

	        for (int i = 0; i < cols; i++) {

	            EditedMethod antecendentTmp = new EditedMethod(matrix.getMatrixDescriptor().getColumnAt(i));
	            int indexOf = branchMethods.indexOf(antecendentTmp);

	            boolean hasDependencies = false;
	            Set<EditedMethod> methodDependencies = new HashSet<EditedMethod>();

	            if (indexOf > -1) {

	                antecendentTmp = branchMethods.get(indexOf);

	                for (int j = 0; j < rows; j++) {

	                    EditedMethod consequentTmp = new EditedMethod(matrix.getMatrixDescriptor().getRowAt(j));
	                    indexOf = branchMethods.indexOf(consequentTmp);

	                    if ((i != j) && (indexOf > -1)) {
	                        for (Cell c : cells) {
	                            if ((c.value >= threshold) && (c.row == j) && (c.col == i)) {
	                                methodDependencies.add(branchMethods.get(indexOf));
	                                hasDependencies = true;
	                            }
	                        }
	                    }
	                }
	            }
	            if (hasDependencies) {
	                dependenciesList.put(antecendentTmp, methodDependencies);
	            }
	        }
	        return dependenciesList;
	    }

	}
