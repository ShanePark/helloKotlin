package grammar.j_access_modifier

private class PrivateClass

open public class PublicClass {
    private val private = 1
    public val public = 2
    protected val protected = 3
    internal val internal = 4
}

// protected is not allowed for class access modifier
//protected class ProtectedClass

// internal is only for same module access
internal class InternalClass
