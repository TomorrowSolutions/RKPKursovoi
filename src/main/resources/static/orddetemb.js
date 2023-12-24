var items = [];
var ids =[];
//const ordId=1;

function addItem() {
    var selObj = document.getElementById("sObjId")
    var selServ = document.getElementById("sServId")
    var newDetail = [selObj.value,selServ.value,1];
    var newItem= [selObj.options[selObj.selectedIndex].text,
        selServ.options[selServ.selectedIndex].text,1]
    var isDupl=checkAndIncrementArray(ids,newDetail,items);
    if(isDupl==false){
        items.push(newItem);
        ids.push(newDetail);
    }
    selObj.value="";
    selServ.value=""
    displayArraysAsTable(items);
    put();
}
function checkAndIncrementArray(arr, newArr,dumbArr) {
    for (let i = 0; i < arr.length; i++) {
      const existingArray = arr[i];
      const temp = dumbArr[i];
      if (existingArray.length === newArr.length  && existingArray.slice(0, -1).every((value, index) => value === newArr[index])) {
        // Array found! Increment the last element of the existing array
        existingArray[existingArray.length - 1]++;
        temp[temp.length-1]++;
        return true
      }
    }
    return false
  }
function display(){
    displayArraysAsTable(items)
    console.log(ids);
}
function clr_lists(){
    ids.length=0;
    items.length=0;
    console.log(ids)
    console.log(items)
    displayArraysAsTable(items)
}
function displayArraysAsTable(arrayOfArrays) {
    // Create a table element
    var table = document.getElementById('tbl-body');
    table.innerHTML="";
    // Iterate through the outer array
    for (let i = 0; i < arrayOfArrays.length; i++) {
    // Create a table row element for each inner array
    const row = document.createElement('tr');
    const cell = document.createElement('td');
        cell.textContent = i;
        // Append the table cell to the table row
    row.appendChild(cell);
    // Iterate through the inner array
    for (let j = 0; j < arrayOfArrays[i].length; j++) {
        // Create a table cell element for each element in the inner array
        const cell = document.createElement('td');
        cell.textContent = arrayOfArrays[i][j];

        // Append the table cell to the table row
        row.appendChild(cell);
    }

    // Append the table row to the table
    table.appendChild(row);
    }

    // Append the table to the body or any desired container element
    //document.body.appendChild(table);
}
function put(){
    let hidList=document.getElementById("hidList");
    hidList.value=ids;
    let inputValue = hidList.value;
    console.log(inputValue);
}