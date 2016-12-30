$(document).ready(function () {
    PopUpHide(".authorization");
    PopUpHide(".betList");
    PopUpHide(".changeMessage");
    PopUpHide(".bankCard");
});

$(document).keydown(function (e) {
    if ($(".authorization").is(":visible") && (e.which == 27 || e.keyCode == 27)) {
        PopUpHide(".authorization");
    }
    if ($(".betList").is(":visible") && (e.which == 27 || e.keyCode == 27)) {
        PopUpHide(".betList");
    }
    if ($(".changeMessage").is(":visible") && (e.which == 27 || e.keyCode == 27)) {
        PopUpHide(".changeMessage");
    }
    if ($(".bankCard").is(":visible") && (e.which == 27 || e.keyCode == 27)) {
        PopUpHide(".bankCard");
    }
})

$(document).mouseup(function (e) {
    if ($(".authorization").has(e.target).length === 0) {
        PopUpHide(".authorization");
    }
    if ($(".betList").has(e.target).length === 0) {
        PopUpHide(".betList");
    }
    if ($(".changeMessage").has(e.target).length === 0) {
        PopUpHide(".changeMessage");
    }
    if ($(".bankCard").has(e.target).length === 0) {
        PopUpHide(".bankCard");
    }
})

function PopUpShow(id) {
    $(id).show();
}

function PopUpHide(id) {
    $(id).hide();
}

function errorUser() {
    PopUpShow('.authorization');
    document.getElementById('mail').style.border = '1px solid red';
    document.getElementById('pwd').style.border = '1px solid red';
    document.getElementById('errBadUser').style.visibility = 'visible';

}

function errorAuth() {
    PopUpShow('.authorization');
    document.getElementById('mail').style.border = '1px solid red';
    document.getElementById('errAuthUser').style.visibility = 'visible';
    document.getElementById('errAuthUser').style.marginLeft = '90px';
}

function errorBan() {
    PopUpShow('.authorization');
    document.getElementById('errBan').style.visibility = 'visible';
    document.getElementById('errBan').style.marginLeft = '125px';
}

function onClick(id) {
    document.getElementById(id).submit();
    return false;
}

function registration() {
    document.getElementById('enterBlock').style.display = 'none';
    document.getElementById('authBlock').style.display = 'block';
    document.getElementById('fogPwd').style.visibility = 'hidden';
    document.getElementById('com').value = 'register';
    reset();
}

function enter() {
    document.getElementById('authBlock').style.display = 'none';
    document.getElementById('enterBlock').style.display = 'block';
    document.getElementById('fogPwd').style.visibility = 'visible';
    document.getElementById('com').value = 'login';
    reset();
}

function betZero() {
    document.getElementById('betButton').disabled = 'true';
}

function changeMessage() {
    PopUpShow('.changeMessage');
}

function betValue(f) {
    if (f.value.indexOf(".") != '-1') {
        f.value = f.value.substring(0, f.value.indexOf(".") + 3);
    }
    if (f.value.indexOf(",") != '-1') {
        f.value = f.value.substring(0, f.value.indexOf(",") + 3);
    }
    f.onkeypress = function (e) {
        if (this.value.indexOf(".") != '-1' || this.value.indexOf(",") != '-1') {
            return !(/[.,]/.test(String.fromCharCode(e.charCode)));
        }
        f.oninput = function () {
            var ind;
            if (this.value.indexOf(".") != '-1' || this.value.indexOf(",") != '-1') {
                ind = 7;
            } else {
                ind = 4;
            }
            if (this.value.length > ind) {
                this.value = this.value.substr(0, ind);
            }
        }
        return !(/[А-Яа-яA-Za-z ]/.test(String.fromCharCode(e.charCode)));
    }
}

function cardNumber(f) {
    f.onkeypress = function (e) {
        return !(/[А-Яа-яA-Za-z .,]/.test(String.fromCharCode(e.charCode)));
    }
    f.oninput = function () {
        if (f.value.length > 16) f.value = f.value.substr(0, 16);
    }
}

function validateAuth() {
    var result = true;

    reset();

    var pwd = document.auth.pwd.value,
        mail = document.auth.mail.value;

    //Mail
    if (!mail) {
        document.getElementById('errFillMail').style.visibility = 'visible';
        document.getElementById('mail').style.border = '1px solid red';
        result = false;
    }

    if (mail && mail.search(/^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/i) !== 0) {
        document.getElementById('errBadMail').style.visibility = 'visible';
        document.getElementById('mail').style.border = '1px solid red';
        result = false;
    }

    //Password
    if (!pwd) {
        document.getElementById('errFillPwd').style.visibility = 'visible';
        document.getElementById('pwd').style.border = '1px solid red';
        result = false;
    }

    if (pwd && pwd.length < 6) {
        document.getElementById('errLenPwd').style.visibility = 'visible';
        document.getElementById('pwd').style.border = '1px solid red';
        document.auth.pwd.value = "";
        result = false;
    }

    if (pwd && pwd.length >= 6 && pwd.search(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/i) !== 0) {
        document.getElementById('errBadPwd').style.visibility = 'visible';
        document.getElementById('pwd').style.border = '1px solid red';
        document.auth.pwd.value = "";
        result = false;
    }

    return result;
}

function reset() {
    document.getElementById('errFillMail').style.visibility = 'hidden';
    document.getElementById('errBadMail').style.visibility = 'hidden';
    document.getElementById('errFillPwd').style.visibility = 'hidden';
    document.getElementById('errLenPwd').style.visibility = 'hidden';
    document.getElementById('errBadPwd').style.visibility = 'hidden';
    document.getElementById('mail').style.border = '1px solid #d9d9d9';
    document.getElementById('pwd').style.border = '1px solid #d9d9d9';

    if (document.getElementById("errBadUser")) {
        document.getElementById('errBadUser').style.visibility = 'hidden';
    }

    if (document.getElementById("errAuthUser")) {
        document.getElementById('errAuthUser').style.visibility = 'hidden';
    }

    if (document.getElementById("errBan")) {
        document.getElementById('errBan').style.visibility = 'hidden';
    }
}

function validateBet() {
    var result = true,
        bet = document.inputBet.amountBet.value,
        minBet = document.inputBet.minBet.value;
    if (!bet || (parseFloat(bet) < parseFloat(minBet) || bet > 9999.99)) {
        errorBet();
        result = false;
    }
    return result;
}

function errorBet() {
    document.getElementById('amountBet').style.border = '1px solid red';
}

function validateUserChange() {
    resetUserChange();
    var result = true,
        newLogin = document.userChange.login.value,
        oldLogin = document.userChange.userOldLogin.value,
        oldPwd = document.userChange.oldPwd.value,
        newPwd = document.userChange.newPwd.value;

    //Login
    if (newLogin.valueOf() == oldLogin.valueOf() && (!oldPwd || !newPwd)) {
        result = false;
    }

    if (newLogin.valueOf() != oldLogin.valueOf() && newLogin.length > 20) {
        errorLoginLength();
        return false;
    }

    //Password
    if (!oldPwd && newPwd) {
        document.getElementById('errFillOldPwd').style.visibility = 'visible';
        document.getElementById('oldPwd').style.border = '1px solid red';
        return false;
    }

    if (newPwd && newPwd.length < 6) {
        document.getElementById('errLenNewPwd').style.visibility = 'visible';
        document.getElementById('newPwd').style.border = '1px solid red';
        document.userChange.newPwd.value = "";
        return false;
    }

    if (newPwd && newPwd.search(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/i) !== 0) {
        document.getElementById('errBadNewPwd').style.visibility = 'visible';
        document.getElementById('newPwd').style.border = '1px solid red';
        document.userChange.newPwd.value = "";
        return false;
    }

    if (oldPwd && oldPwd.length < 6) {
        document.getElementById('errLenOldPwd').style.visibility = 'visible';
        document.getElementById('oldPwd').style.border = '1px solid red';
        document.userChange.oldPwd.value = "";
        return false;
    }

    if (oldPwd && oldPwd.search(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/i) !== 0) {
        document.getElementById('errBadOldPwd').style.visibility = 'visible';
        document.getElementById('oldPwd').style.border = '1px solid red';
        document.userChange.oldPwd.value = "";
        return false;
    }

    if (oldPwd && newPwd && oldPwd.valueOf() == newPwd.valueOf()) {
        document.getElementById('errOldNewPwd').style.visibility = 'visible';
        document.getElementById('oldPwd').style.border = '1px solid red';
        document.userChange.oldPwd.value = "";
        document.getElementById('newPwd').style.border = '1px solid red';
        document.userChange.newPwd.value = "";
        return false;
    }

    return result;
}

function resetUserChange() {
    document.getElementById('login').style.border = '1px solid #d9d9d9';
    document.getElementById('oldPwd').style.border = '1px solid #d9d9d9';
    document.getElementById('newPwd').style.border = '1px solid #d9d9d9';
    document.getElementById('errLoginLength').style.visibility = 'hidden';
    document.getElementById('errFillOldPwd').style.visibility = 'hidden';
    document.getElementById('errLenNewPwd').style.visibility = 'hidden';
    document.getElementById('errBadNewPwd').style.visibility = 'hidden';
    document.getElementById('errLenOldPwd').style.visibility = 'hidden';
    document.getElementById('errBadOldPwd').style.visibility = 'hidden';
    document.getElementById('errOldNewPwd').style.visibility = 'hidden';
}

function errorLoginLength() {
    document.getElementById('login').style.border = '1px solid red';
    document.getElementById('errLoginLength').style.visibility = 'visible';
}

function errorLogin() {
    document.getElementById('login').style.border = '1px solid red';
    document.getElementById('errLogin').style.visibility = 'visible';
}

function errorExistLogin() {
    document.getElementById('login').style.border = '1px solid red';
    document.getElementById('errDoubleLogin').style.visibility = 'visible';
}

function errorPwd() {
    document.getElementById('oldPwd').style.border = '1px solid red';
    document.getElementById('newPwd').style.border = '1px solid red';
    document.getElementById('errPwd').style.visibility = 'visible';
}

function validateCard() {
    resetErrorCard();
    var result = true,
        cardNum = document.card.cardNum.value,
        amount = document.card.amount.value;

    //Number card
    if (!cardNum) {
        errCardNumber();
        result = false;
    }

    if (cardNum && cardNum.length != 16) {
        errCardNumber();
        result = false;
    }

    //Amount
    if (!amount) {
        errAmount();
        result = false;
    }

    if (amount && (amount < 10.00 || amount > 9999.99)) {
        errAmount();
        result = false;
    }

    return result;
}

function errCardNumber() {
    PopUpShow('.bankCard');
    document.getElementById('errCardNum').style.visibility = 'visible';
    document.getElementById('cardNum').style.border = '1px solid red';
}

function errAmount() {
    PopUpShow('.bankCard');
    document.getElementById('errAmount').style.visibility = 'visible';
    document.getElementById('amount').style.border = '1px solid red';
}

function errCard() {
    errCardNumber();
    errAmount();
}

function resetErrorCard() {
    document.getElementById('errCardNum').style.visibility = 'hidden';
    document.getElementById('cardNum').style.border = '1px solid #d9d9d9';
    document.getElementById('errAmount').style.visibility = 'hidden';
    document.getElementById('amount').style.border = '1px solid #d9d9d9';
}

function getFileParam() {
    var file = document.getElementById('photo').files[0];
    if (file) {
        if (/\.(jpe?g)$/i.test(file.name)) {
            var elPreview = document.getElementById('preview');
            elPreview.innerHTML = '';
            var newImg = document.createElement('img');
            newImg.className = "preview-img";
            if (typeof file.getAsDataURL == 'function') {
                if (file.getAsDataURL().substr(0, 11) == 'data:image/') {
                    newImg.setAttribute('src', file.getAsDataURL());
                    elPreview.appendChild(newImg);
                }
            }
            else {
                var reader = new FileReader();
                reader.onloadend = function (evt) {
                    if (evt.target.readyState == FileReader.DONE) {
                        newImg.setAttribute('src', evt.target.result);
                        elPreview.appendChild(newImg);
                    }
                };
                var blob;
                if (file.slice) {
                    blob = file.slice(0, file.size);
                }
                else if (file.webkitSlice) {
                    blob = file.webkitSlice(0, file.size);
                }
                else if (file.mozSlice) {
                    blob = file.mozSlice(0, file.size);
                }
                reader.readAsDataURL(blob);
            }
        }
    }
}

function changeActionType() {
    var type = document.newLot.type.value;
    if (type == 3) {
        document.getElementById('priceStep').disabled = 1;
        document.getElementById('priceBlitz').disabled = 1;
        document.getElementById('priceStep').style.border = '1px solid #d9d9d9';
        document.getElementById('priceBlitz').style.border = '1px solid #d9d9d9';
        document.getElementById('errPriceStep').style.visibility = 'hidden';
        document.getElementById('errPriceBlitz').style.visibility = 'hidden';
    } else {
        if (type == 1) {
            document.getElementById('priceStep').disabled = 0;
            document.getElementById('priceBlitz').disabled = 1;
            document.getElementById('priceBlitz').style.border = '1px solid #d9d9d9';
            document.getElementById('errPriceBlitz').style.visibility = 'hidden';
        } else {
            document.getElementById('priceStep').disabled = 0;
            document.getElementById('priceBlitz').disabled = 0;
        }
    }
}

function validateNewLot() {
    resetErrorLot();
    var result = true,
        title = document.newLot.title.value,
        category = document.newLot.category.value,
        priceStart = document.newLot.priceStart.value,
        type = document.newLot.type.value,
        priceStep = document.newLot.priceStep.value,
        priceBlitz = document.newLot.priceBlitz.value,
        term = document.newLot.term.value,
        condition = document.newLot.condition.value,
        description = document.newLot.description.value,
        photo = document.newLot.photo.value;
    if (!title || title.length > 90) {
        document.getElementById('title').style.border = '1px solid red';
        document.getElementById('errTitle').style.visibility = 'visible';
        result = false;
    }
    if (category.length > 3) {
        document.getElementById('category').style.border = '1px solid red';
        document.getElementById('errCategory').style.visibility = 'visible';
        result = false;
    }
    if (!priceStart || priceStart <= 0 || priceStart > 9999.99) {
        document.getElementById('priceStart').style.border = '1px solid red';
        document.getElementById('errPriceStart').style.visibility = 'visible';
        result = false;
    }
    if (type.length > 3) {
        document.getElementById('type').style.border = '1px solid red';
        document.getElementById('errType').style.visibility = 'visible';
        result = false;
    }
    if (priceStep && (priceStep <= 0 || priceStep > 9999.99)) {
        document.getElementById('priceStep').style.border = '1px solid red';
        document.getElementById('errPriceStep').style.visibility = 'visible';
        result = false;
    }
    if ((!priceBlitz || priceBlitz <= 0 || priceBlitz > 9999.99) && type != 3 && type != 1) {
        document.getElementById('priceBlitz').style.border = '1px solid red';
        document.getElementById('errPriceBlitz').style.visibility = 'visible';
        result = false;
    }
    if (term.length > 3) {
        document.getElementById('term').style.border = '1px solid red';
        document.getElementById('errTerm').style.visibility = 'visible';
        result = false;
    }
    if (condition.length > 3) {
        document.getElementById('condition').style.border = '1px solid red';
        document.getElementById('errCondition').style.visibility = 'visible';
        result = false;
    }
    if (!description) {
        document.getElementById('description').style.border = '1px solid red';
        document.getElementById('errDescription').style.visibility = 'visible';
        result = false;
    }
    if (!photo) {
        document.getElementById('preview').style.border = '1px solid red';
        document.getElementById('errPhoto').style.visibility = 'visible';
        result = false;
    }
    return result;
}

function resetErrorLot() {
    document.getElementById('title').style.border = '1px solid #d9d9d9';
    document.getElementById('category').style.border = '1px solid #d9d9d9';
    document.getElementById('priceStart').style.border = '1px solid #d9d9d9';
    document.getElementById('type').style.border = '1px solid #d9d9d9';
    document.getElementById('priceStep').style.border = '1px solid #d9d9d9';
    document.getElementById('priceBlitz').style.border = '1px solid #d9d9d9';
    document.getElementById('term').style.border = '1px solid #d9d9d9';
    document.getElementById('condition').style.border = '1px solid #d9d9d9';
    document.getElementById('description').style.border = '1px solid #d9d9d9';
    document.getElementById('preview').style.border = '1px solid #d9d9d9';
    document.getElementById('errTitle').style.visibility = 'hidden';
    document.getElementById('errCategory').style.visibility = 'hidden';
    document.getElementById('errPriceStart').style.visibility = 'hidden';
    document.getElementById('errType').style.visibility = 'hidden';
    document.getElementById('errPriceStep').style.visibility = 'hidden';
    document.getElementById('errPriceBlitz').style.visibility = 'hidden';
    document.getElementById('errTerm').style.visibility = 'hidden';
    document.getElementById('errCondition').style.visibility = 'hidden';
    document.getElementById('errDescription').style.visibility = 'hidden';
    document.getElementById('errPhoto').style.visibility = 'hidden';
    document.getElementById('errLot').style.visibility = 'hidden';
}

function errNewLot() {
    document.getElementById('errLot').style.visibility = 'visible';
}

function visibleAdmin(id) {
    document.getElementById('adminUser').style.display = 'none';
    document.getElementById('adminLot').style.display = 'none';
    document.getElementById('adminPwd').style.display = 'none';
    document.getElementById(id).style.display = 'block';
}

