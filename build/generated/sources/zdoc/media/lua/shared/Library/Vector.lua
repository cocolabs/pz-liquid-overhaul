---@class Vector : java.util.Vector
---@field protected elementData Object[]
---@field protected elementCount int
---@field protected capacityIncrement int
---@field private serialVersionUID long
---@field private MAX_ARRAY_SIZE int
Vector = {}

---@public
---@param arg0 Object
---@param arg1 int
---@return void
function Vector:insertElementAt(arg0, arg1) end

---@public
---@return void
function Vector:removeAllElements() end

---@public
---@param arg0 Object
---@return int
---@overload fun(arg0:Object, arg1:int)
function Vector:lastIndexOf(arg0) end

---@public
---@param arg0 Object
---@param arg1 int
---@return int
function Vector:lastIndexOf(arg0, arg1) end

---@public
---@return String
function Vector:toString() end

---@public
---@param arg0 Object
---@return boolean
---@overload fun(arg0:int, arg1:Object)
function Vector:add(arg0) end

---@public
---@param arg0 int
---@param arg1 Object
---@return void
function Vector:add(arg0, arg1) end

---@public
---@param arg0 Object
---@param arg1 int
---@return void
function Vector:setElementAt(arg0, arg1) end

---@public
---@param arg0 int
---@param arg1 Object
---@return Object
function Vector:set(arg0, arg1) end

---@public
---@param arg0 Consumer|Unknown
---@return void
function Vector:forEach(arg0) end

---@public
---@param arg0 Collection|Unknown
---@return boolean
function Vector:retainAll(arg0) end

---@public
---@param arg0 Comparator|Unknown
---@return void
function Vector:sort(arg0) end

---@public
---@param arg0 int
---@return void
function Vector:removeElementAt(arg0) end

---@public
---@param arg0 Object[]
---@return void
function Vector:copyInto(arg0) end

---@public
---@param arg0 int
---@return Object
function Vector:get(arg0) end

---@protected
---@param arg0 int
---@param arg1 int
---@return void
function Vector:removeRange(arg0, arg1) end

---@public
---@return int
function Vector:size() end

---@public
---@param arg0 Collection|Unknown
---@return boolean
---@overload fun(arg0:int, arg1:Collection|Unknown)
function Vector:addAll(arg0) end

---@public
---@param arg0 int
---@param arg1 Collection|Unknown
---@return boolean
function Vector:addAll(arg0, arg1) end

---@public
---@return void
function Vector:trimToSize() end

---@public
---@param arg0 Object
---@return boolean
function Vector:equals(arg0) end

---@public
---@return Object
function Vector:lastElement() end

---@public
---@return Object[]
---@overload fun(arg0:Object[])
function Vector:toArray() end

---@public
---@param arg0 Object[]
---@return Object[]
function Vector:toArray(arg0) end

---@public
---@return void
function Vector:clear() end

---@public
---@return Spliterator|Unknown
function Vector:spliterator() end

---@public
---@param arg0 UnaryOperator|Unknown
---@return void
function Vector:replaceAll(arg0) end

---@public
---@param arg0 Object
---@return void
function Vector:addElement(arg0) end

---@public
---@param arg0 int
---@return void
function Vector:ensureCapacity(arg0) end

---@private
---@param arg0 int
---@return void
function Vector:grow(arg0) end

---@private
---@param arg0 int
---@return void
function Vector:ensureCapacityHelper(arg0) end

---@public
---@return int
function Vector:capacity() end

---@private
---@param arg0 ObjectOutputStream
---@return void
function Vector:writeObject(arg0) end

---@public
---@param arg0 int
---@return Object
function Vector:elementAt(arg0) end

---@public
---@return Object
function Vector:clone() end

---@public
---@param arg0 int
---@return void
function Vector:setSize(arg0) end

---@public
---@param arg0 Collection|Unknown
---@return boolean
function Vector:removeAll(arg0) end

---@public
---@return int
function Vector:hashCode() end

---@public
---@param arg0 Object
---@return int
---@overload fun(arg0:Object, arg1:int)
function Vector:indexOf(arg0) end

---@public
---@param arg0 Object
---@param arg1 int
---@return int
function Vector:indexOf(arg0, arg1) end

---@private
---@param arg0 ObjectInputStream
---@return void
function Vector:readObject(arg0) end

---@public
---@return Iterator|Unknown
function Vector:iterator() end

---@public
---@param arg0 Predicate|Unknown
---@return boolean
function Vector:removeIf(arg0) end

---@public
---@return boolean
function Vector:isEmpty() end

---@private
---@param arg0 int
---@return int
function Vector:hugeCapacity(arg0) end

---@public
---@return ListIterator|Unknown
---@overload fun(arg0:int)
function Vector:listIterator() end

---@public
---@param arg0 int
---@return ListIterator|Unknown
function Vector:listIterator(arg0) end

---@public
---@param arg0 Object
---@return boolean
function Vector:contains(arg0) end

---@public
---@param arg0 Object
---@return boolean
---@overload fun(arg0:int)
function Vector:remove(arg0) end

---@public
---@param arg0 int
---@return Object
function Vector:remove(arg0) end

---@public
---@param arg0 Object
---@return boolean
function Vector:removeElement(arg0) end

---@public
---@param arg0 Collection|Unknown
---@return boolean
function Vector:containsAll(arg0) end

---@param arg0 int
---@return Object
function Vector:elementData(arg0) end

---@public
---@return Object
function Vector:firstElement() end

---@public
---@return Enumeration|Unknown
function Vector:elements() end

---@public
---@param arg0 int
---@param arg1 int
---@return List|Unknown
function Vector:subList(arg0, arg1) end
