---@class ArrayList : java.util.ArrayList
---@field private serialVersionUID long
---@field private DEFAULT_CAPACITY int
---@field private EMPTY_ELEMENTDATA Object[]
---@field private DEFAULTCAPACITY_EMPTY_ELEMENTDATA Object[]
---@field elementData Object[]
---@field private size int
---@field private MAX_ARRAY_SIZE int
ArrayList = {}

---@public
---@param arg0 UnaryOperator|Unknown
---@return void
function ArrayList:replaceAll(arg0) end

---@private
---@param arg0 int
---@return void
function ArrayList:rangeCheck(arg0) end

---@private
---@param arg0 Object[]
---@param arg1 int
---@return int
function ArrayList:calculateCapacity(arg0, arg1) end

---@public
---@param arg0 int
---@return void
function ArrayList:ensureCapacity(arg0) end

---@private
---@param arg0 Collection|Unknown
---@param arg1 boolean
---@return boolean
function ArrayList:batchRemove(arg0, arg1) end

---@private
---@param arg0 int
---@return void
function ArrayList:grow(arg0) end

---@public
---@return Spliterator|Unknown
function ArrayList:spliterator() end

---@public
---@return Object[]
---@overload fun(arg0:Object[])
function ArrayList:toArray() end

---@public
---@param arg0 Object[]
---@return Object[]
function ArrayList:toArray(arg0) end

---@protected
---@param arg0 int
---@param arg1 int
---@return void
function ArrayList:removeRange(arg0, arg1) end

---@public
---@return ListIterator|Unknown
---@overload fun(arg0:int)
function ArrayList:listIterator() end

---@public
---@param arg0 int
---@return ListIterator|Unknown
function ArrayList:listIterator(arg0) end

---@public
---@return boolean
function ArrayList:isEmpty() end

---@public
---@param arg0 Collection|Unknown
---@return boolean
---@overload fun(arg0:int, arg1:Collection|Unknown)
function ArrayList:addAll(arg0) end

---@public
---@param arg0 int
---@param arg1 Collection|Unknown
---@return boolean
function ArrayList:addAll(arg0, arg1) end

---@public
---@return Iterator|Unknown
function ArrayList:iterator() end

---@public
---@param arg0 Comparator|Unknown
---@return void
function ArrayList:sort(arg0) end

---@public
---@param arg0 Object
---@return int
function ArrayList:lastIndexOf(arg0) end

---@public
---@param arg0 Object
---@return boolean
---@overload fun(arg0:int, arg1:Object)
function ArrayList:add(arg0) end

---@public
---@param arg0 int
---@param arg1 Object
---@return void
function ArrayList:add(arg0, arg1) end

---@private
---@param arg0 int
---@return String
function ArrayList:outOfBoundsMsg(arg0) end

---@param arg0 int
---@return Object
function ArrayList:elementData(arg0) end

---@public
---@param arg0 int
---@return Object
---@overload fun(arg0:Object)
function ArrayList:remove(arg0) end

---@public
---@param arg0 Object
---@return boolean
function ArrayList:remove(arg0) end

---@public
---@param arg0 Object
---@return boolean
function ArrayList:contains(arg0) end

---@private
---@param arg0 int
---@return void
function ArrayList:ensureExplicitCapacity(arg0) end

---@public
---@param arg0 Object
---@return int
function ArrayList:indexOf(arg0) end

---@private
---@param arg0 int
---@return void
function ArrayList:ensureCapacityInternal(arg0) end

---@public
---@param arg0 Consumer|Unknown
---@return void
function ArrayList:forEach(arg0) end

---@public
---@param arg0 Collection|Unknown
---@return boolean
function ArrayList:retainAll(arg0) end

---@public
---@param arg0 int
---@param arg1 int
---@return List|Unknown
function ArrayList:subList(arg0, arg1) end

---@private
---@param arg0 int
---@return void
function ArrayList:fastRemove(arg0) end

---@private
---@param arg0 ObjectOutputStream
---@return void
function ArrayList:writeObject(arg0) end

---@public
---@param arg0 int
---@param arg1 Object
---@return Object
function ArrayList:set(arg0, arg1) end

---@public
---@param arg0 int
---@return Object
function ArrayList:get(arg0) end

---@public
---@param arg0 Collection|Unknown
---@return boolean
function ArrayList:removeAll(arg0) end

---@param arg0 int
---@param arg1 int
---@param arg2 int
---@return void
function ArrayList:subListRangeCheck(arg0, arg1, arg2) end

---@private
---@param arg0 int
---@return void
function ArrayList:rangeCheckForAdd(arg0) end

---@private
---@param arg0 ObjectInputStream
---@return void
function ArrayList:readObject(arg0) end

---@public
---@return void
function ArrayList:trimToSize() end

---@public
---@return Object
function ArrayList:clone() end

---@public
---@return void
function ArrayList:clear() end

---@public
---@param arg0 Predicate|Unknown
---@return boolean
function ArrayList:removeIf(arg0) end

---@private
---@param arg0 int
---@return int
function ArrayList:hugeCapacity(arg0) end

---@public
---@return int
function ArrayList:size() end
