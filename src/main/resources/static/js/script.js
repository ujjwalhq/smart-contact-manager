console.log("script loaded");

//Start code for theme change 

let currentTheme = getTheme();

// initial apply

document.addEventListener('DOMContentLoaded',()=>{
  changeTheme();
})

function changeTheme(){

  // apply theme
  document.querySelector("html").classList.remove("light", "dark");
  document.querySelector("html").classList.add(currentTheme);

  console.log("theme applied:", currentTheme);

  const changeThemeButton = document.querySelector('#theme_change_button');

  // set button text
  changeThemeButton.querySelector('span').textContent =
    currentTheme == "light" ? "Dark" : "Light";

  // listener (only once)
  changeThemeButton.addEventListener("click",(event)=>{

    const oldTheme = currentTheme;

    if(currentTheme == "dark"){
      currentTheme = "light";
    } else{
      currentTheme = "dark";
    }

    changePageTheme(currentTheme, oldTheme);
  });
}

// set theme
function setTheme(theme){
  localStorage.setItem("theme", theme);
}

// get theme
function getTheme(){
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

// change current page theme
function changePageTheme(theme, oldTheme){

    // update local storage
    setTheme(theme);

    // update UI
    document.querySelector('html').classList.remove(oldTheme);
    document.querySelector('html').classList.add(theme);

    console.log("theme changed:", theme);

    // update button text
    document.querySelector('#theme_change_button span').textContent =
      theme == "light" ? "Dark" : "Light";
}

//End of Theme Change code