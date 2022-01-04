// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract B {

    uint public age = 80;
    string private name = "wxy";

    function getName() public view returns(string memory){
        return name;
    }

}
