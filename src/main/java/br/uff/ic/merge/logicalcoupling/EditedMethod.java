package br.uff.ic.merge.logicalcoupling;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Objects;

/**
 *
 * @author Cristiane
 */
public class EditedMethod {
    
    private String methodName;

    public EditedMethod(String methodName) {
        this.methodName = methodName;

    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

   /*  public List<MyMethodDeclaration> getEditedMethods() {
        return editedMethods;
    }

    public void setEditedMethods(List<MyMethodDeclaration> editedMethods) {
        this.editedMethods = editedMethods;
    }*/
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.methodName);
        return hash;
    }

    //this method compare file names
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EditedMethod other = (EditedMethod) obj;
        return Objects.equals(this.methodName, other.methodName);
    }

    @Override
    public String toString() {
        return this.getMethodName();
    }

}
