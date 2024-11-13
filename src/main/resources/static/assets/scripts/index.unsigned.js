const $cover = document.getElementById("cover");
const $main = document.getElementById("main");
const $registerForm = document.getElementById("registerForm");

{
    const $content = $main.querySelector(":scope > .content");
    const $loginForm = $content.querySelector(":scope > .login-form");
    const $menu = $loginForm.querySelector(":scope > .menu");
    $menu.querySelector(':scope > .item > [rel="register"]').onclick = (e) => {
        e.preventDefault();
        $cover.onclick = () => {
            $cover.hide();
            $registerForm.hide();
        };
        $cover.show();
        $registerForm.reset();
        $registerForm.show();
    };
}
window.onload = () => {
    const $content = $main.querySelector(':scope > .content');
    const $logo = $content.querySelector(':scope > .logo');
    const $loginForm = $content.querySelector(':scope > .login-form');
    setTimeout(() => $logo.show(), 100);
    setTimeout(() => $loginForm.show(), 1000);
};