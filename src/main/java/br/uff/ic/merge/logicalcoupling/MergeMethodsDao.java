package br.uff.ic.merge.logicalcoupling;

import java.io.File;

/**
 * This class gets the information about the merge - commits on Branch1, on
 * Branch2 e all history before the branch
 *
 * @author Cristiane
 */
public class MergeMethodsDao { 

    //get the commits on Branch1 (base to parent 1) and on Branch2 (base to parent 2)
    public MergeMethods getMerge(String hash, File path) {
        MergeMethods merge = new MergeMethods(hash, path);
        String hashParents = Git.getParents2(path.getPath(), hash);
        merge.setHashBase(Git.getMergeBase(path.getPath(), hashParents.split(" ")[0], hashParents.split(" ")[1]));
        //merge.setHashBase(RunGit.getResult("git merge-base " + hashParents.split(" ")[0] + " " + hashParents.split(" ")[1], path));
        merge.setParents(hashParents.split(" ")[0], hashParents.split(" ")[1]);
        return merge;
    }

}

