public class a1 {
    VariableDeclarationStatement stmt= (VariableDeclarationStatement) frag.getParent();
    return stmt.getType().resolveBinding();
}
