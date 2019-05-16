package br.uff.ic.merge.logicalcoupling;

//import br.uff.ic.gems.tipmerge.model.EditedFile;
import org.eclipse.jgit.lib.Repository;
//import br.uff.ic.gems.tipmerge.util.RunGit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import RepositoryImporter.ClassNode;
import RepositoryImporter.CommitNode;
import RepositoryImporter.FileNode;
import RepositoryImporter.FunctionNode;
import RepositoryImporter.RepositoryNode;

/**
 * This class gets list of methods changed between two commits hash
 *
 * @author Cristiane
 */
public class EditedMethodsDao { //classe não utilizada

	//We are using this code. This method gets one list of Editedfiles (String fileName) changed between two commits hash
	public List<EditedMethod> getMethods(List<String> commitsLeft, RepositoryNode _repo, Repository gitRepository) throws IOException {

		Set<EditedMethod> methods = new HashSet<EditedMethod>();

		org.eclipse.jgit.api.Git git = new org.eclipse.jgit.api.Git(gitRepository);

		Iterable<RevCommit> commits = null;

		for (String commitLeft : commitsLeft) {

			try {
				commits = git.log().call();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (RevCommit commit : commits) {
				if (commit.getName().contains(commitLeft)) {
					CommitNode commitNodeLeft = new CommitNode(commit);
					commitNodeLeft.parse(_repo);
					List<FileNode> fileNodesLeft = commitNodeLeft.getFiles();
					for (FileNode fileNodeLeft : fileNodesLeft) {
						fileNodeLeft.Parse(gitRepository);//atualiza CLassNodes
						if((fileNodeLeft.newName.endsWith(".java")) || (fileNodeLeft.oldName.endsWith(".java"))){

							List<ClassNode> classNodes = fileNodeLeft.getClasses();

							for (ClassNode classNode : classNodes) {
								List<ClassNode> classNodesNew = classNode.Parse(fileNodeLeft, gitRepository);
								for (ClassNode classNodeNew : classNodesNew) {
									List<FunctionNode> functionNodes = classNodeNew.getFunctions();
									for (FunctionNode functionNode : functionNodes) {
										methods.add(new EditedMethod(classNodeNew.getName() + "$" + functionNode.getName()));
									}

								}
							}
						}
					}

				}

			}

		}
		return new ArrayList<EditedMethod>(methods);
	}
}
