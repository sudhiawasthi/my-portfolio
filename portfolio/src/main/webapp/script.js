// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 *Contacts server for content and add that to page
 */
function getContent() {
    
    fetch('/data').then(response => response.json()).then(messages => {

        // Gets the element to add the comments
         const comments = document.getElementById('content-container');
         comments.innerHTML = '';
         
        //Creates list elements
         for(var commentNum = 0 ; commentNum < 3 ; commentNum++)
         {
             comments.appendChild(
                createListElement(messages[commentNum]));
         }

        });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}
