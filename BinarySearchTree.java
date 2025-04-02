/**
 * Generic Binary Search Tree implementation
 * Supports operations on any comparable data type
 */
public class BinarySearchTree<T extends Comparable<T>> {
    
    private NodeType<T> root; // Root node of the tree
    
    /**
     * Constructor to initialize an empty tree
     */
    public BinarySearchTree() {
        root = null;
    }
    
    /**
     * Insert a node with the value of key into the tree
     * No duplicates are allowed
     * 
     * @param key The value to insert
     */
    public void insert(T key) {
        // Special case: If tree is empty, create root node
        if (root == null) {
            root = new NodeType<>(key);
            return;
        }
        
        NodeType<T> current = root;
        NodeType<T> parent = null;
        boolean goLeft = false;
        
        // Find the correct position to insert
        while (current != null) {
            // Compare key with current node's value
            int compareResult = key.compareTo(current.info);
            
            if (compareResult == 0) {
                // Item already exists
                System.out.println("The item already exists in the tree.");
                return;
            }
            
            parent = current;
            
            if (compareResult < 0) {
                // If key should go left
                current = current.left;
                goLeft = true;
            } else {
                // If key should go right  
                current = current.right;
                goLeft = false;
            }
        }
        
        // Create new node and insert it at the correct position
        NodeType<T> newNode = new NodeType<>(key);
        
        if (goLeft) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }
    
    /**
     * Delete a node with key value from the tree
     * If key not found, tree remains unchanged
     * 
     * @param key The value to delete
     */
    public void delete(T key) {
        if (root == null) {
            System.out.println("The number is not present in the tree");
            return;
        }
        
        // Variables to keep track of parent and current node
        NodeType<T> parent = null;
        NodeType<T> current = root;
        boolean isLeftChild = false;
        
        // Search for the node to delete
        while (current != null) {
            int compareResult = key.compareTo(current.info);
            
            if (compareResult == 0) {
                // Node found, now delete it
                break;
            }
            
            parent = current;
            
            if (compareResult < 0) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
        }
        
        // If key not found
        if (current == null) {
            System.out.println("The number is not present in the tree");
            return;
        }
        
        // Case 1: Node is a leaf (no children)
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        // Case 2: Node has one child (right child)
        else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        }
        // Case 3: Node has one child (left child)
        else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        }
        // Case 4: Node has two children
        else {
            // Find the successor (smallest value in right subtree)
            NodeType<T> successor = current.right;
            NodeType<T> successorParent = current;
            
            // Find the leftmost node in the right subtree
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            
            // If successor is not the immediate right child
            if (successorParent != current) {
                successorParent.left = successor.right;
                successor.right = current.right;
            }
            
            // Replace the deleted node with successor
            successor.left = current.left;
            
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
        }
    }
    
    /**
     * Check if an item exists in the tree
     * 
     * @param item Value to search for
     * @return true if found, false otherwise
     */
    public boolean retrieve(T item) {
        NodeType<T> current = root;
        
        while (current != null) {
            int compareResult = item.compareTo(current.info);
            
            if (compareResult == 0) {
                return true; // Item found
            } else if (compareResult < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        
        return false; // Item not found
    }
    
    /**
     * Print the tree using in-order traversal
     * This will display elements in sorted order
     */
    public void inOrder() {
        System.out.print("In-order: ");
        inOrderTraversal(root);
        System.out.println();
    }
    
    /**
     * Helper method for in-order traversal
     * Uses recursion for simplicity and correctness
     * 
     * @param node Current node in traversal
     */
    private void inOrderTraversal(NodeType<T> node) {
        if (node != null) {
            // First visit left subtree
            inOrderTraversal(node.left);
            // Then visit current node
            System.out.print(node.info + " ");
            // Finally visit right subtree
            inOrderTraversal(node.right);
        }
    }
    
    /**
     * Print nodes that have exactly one child (single parents)
     */
    public void getSingleParent() {
        System.out.print("Single Parents: ");
        
        // If tree is empty, just print a newline
        if (root == null) {
            System.out.println();
            return;
        }
        
        // Use an array-based queue for level-order traversal
        NodeType<T>[] queue = new NodeType[100]; // Assume max 100 nodes for simplicity
        int front = 0;
        int rear = 0;
        
        queue[rear++] = root;
        
        while (front < rear) {
            NodeType<T> current = queue[front++];
            
            // Check if node has exactly one child
            if ((current.left == null && current.right != null) || 
                (current.left != null && current.right == null)) {
                System.out.print(current.info + " ");
            }
            
            // Add children to queue
            if (current.left != null) {
                queue[rear++] = current.left;
            }
            if (current.right != null) {
                queue[rear++] = current.right;
            }
        }
        
        System.out.println();
    }
    
    /**
     * Count and print the number of leaf nodes in the tree
     * 
     * @return Number of leaf nodes
     */
    public int getNumLeafNodes() {
        int count = 0;
        
        // If tree is empty, return 0
        if (root == null) {
            System.out.println("The number of leaf nodes are " + count);
            return count;
        }
        
        // Use an array-based queue for level-order traversal
        NodeType<T>[] queue = new NodeType[100]; // Assume max 100 nodes for simplicity
        int front = 0;
        int rear = 0;
        
        queue[rear++] = root;
        
        while (front < rear) {
            NodeType<T> current = queue[front++];
            
            // Check if node is a leaf (no children)
            if (current.left == null && current.right == null) {
                count++;
            }
            
            // Add children to queue
            if (current.left != null) {
                queue[rear++] = current.left;
            }
            if (current.right != null) {
                queue[rear++] = current.right;
            }
        }
        
        System.out.println("The number of leaf nodes are " + count);
        return count;
    }
    
    /**
     * Print the cousins of a given node
     * Cousins are nodes at the same level that share the same grandparent but have different parents
     * 
     * @param item Value to find cousins of
     */
    public void getCousins(T item) {
        System.out.print(item + " cousins: ");
        
        // If tree is empty or only has root, no cousins possible
        if (root == null || (root.left == null && root.right == null)) {
            System.out.println();
            return;
        }
        
        // First find the node, its parent, and its level
        int nodeLevel = -1;
        NodeType<T> nodeParent = null;
        
        // Use an array-based queue for level-order traversal
        NodeType<T>[] queue = new NodeType[100]; // Assume max 100 nodes for simplicity
        int front = 0;
        int rear = 0;
        
        // Store both node and its parent
        queue[rear++] = root;
        queue[rear++] = null; // Root has no parent
        
        // Also keep track of level
        int level = 0;
        int levelNodes = 1; // Nodes at current level
        int nextLevelNodes = 0; // Nodes at next level
        
        while (front < rear) {
            NodeType<T> current = queue[front++];
            NodeType<T> parent = queue[front++];
            
            if (current.info.compareTo(item) == 0) {
                nodeLevel = level;
                nodeParent = parent;
                break;
            }
            
            if (current.left != null) {
                queue[rear++] = current.left;
                queue[rear++] = current;
                nextLevelNodes++;
            }
            
            if (current.right != null) {
                queue[rear++] = current.right;
                queue[rear++] = current;
                nextLevelNodes++;
            }
            
            levelNodes--;
            if (levelNodes == 0) {
                level++;
                levelNodes = nextLevelNodes;
                nextLevelNodes = 0;
            }
        }
        
        // If node not found or is root, no cousins
        if (nodeLevel <= 0 || nodeParent == null) {
            System.out.println();
            return;
        }
        
        // Find all nodes at the same level
        front = 0;
        rear = 0;
        level = 0;
        levelNodes = 1;
        nextLevelNodes = 0;
        
        queue[rear++] = root;
        queue[rear++] = null;
        
        while (front < rear) {
            NodeType<T> current = queue[front++];
            NodeType<T> parent = queue[front++];
            
            if (level == nodeLevel && parent != null && parent != nodeParent) {
                // Check if parent's parent is the same as nodeParent's parent
                
                // Get grandparent for current node
                NodeType<T> grandparent = null;
                for (int i = 0; i < rear; i += 2) {
                    if (queue[i] == parent) {
                        grandparent = queue[i + 1];
                        break;
                    }
                }
                
                // Get grandparent for target node
                NodeType<T> nodeGrandparent = null;
                for (int i = 0; i < rear; i += 2) {
                    if (queue[i] == nodeParent) {
                        nodeGrandparent = queue[i + 1];
                        break;
                    }
                }
                
                // If same grandparent, they're cousins
                if (grandparent != null && nodeGrandparent != null && grandparent == nodeGrandparent) {
                    System.out.print(current.info + " ");
                }
            }
            
            if (current.left != null) {
                queue[rear++] = current.left;
                queue[rear++] = current;
                nextLevelNodes++;
            }
            
            if (current.right != null) {
                queue[rear++] = current.right;
                queue[rear++] = current;
                nextLevelNodes++;
            }
            
            levelNodes--;
            if (levelNodes == 0) {
                level++;
                levelNodes = nextLevelNodes;
                nextLevelNodes = 0;
                
                // If we've gone past the target level, we're done
                if (level > nodeLevel) {
                    break;
                }
            }
        }
        
        System.out.println();
    }
}
