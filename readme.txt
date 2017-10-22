SurvivalGame

Info
----

Autor hry: gcx11

Hra je napsaná v Javě a pro spuštění vyžaduje Javu 1.8. Cílem je udržet herní postavu co nejdéle při životě.

Ovládání
--------

Hráč se pohybuje pomocí kláves WADS:
    W - nahoru
    A - doleva
    D - doprava
    S - dolů

Pokud hráč navíc stiskne klávesu Shift, tak může dočasně běžet mnohem rychleji,
za cenu rychlejšího tempa hladovění.

S okolím se interaguje pomocí levého tlačítka myši. Hlavní inventář se zavírá/otevírá
klávesou I, inventář pro "craftění" předmětů se dá zavřít tlačítkem C. Z hlavního inventáře
lze věc odstranit kliknutím pravým tlačítkem.

Debugovací informace jsou dostupné přes klávesu B. Hra se pozastavuje automaticky, pokud okno
ztratí focus anebo se během hry stiskne klávesa P.

Fyziologie
----------

Hráč se musí starat o dva herní ukazatele, aby byl naživu. Jedná se o ukazatele jeho zdraví a sytosti.

Zdraví se snižuje bojováním s monstry na která při průzkumu krajiny narazí. Zdraví se obnovuje samo,
pokud je hráč dostatečně sytý. Alternativně se dá doplnit lektvarem, který se připraví z 10 bílých květů a 10 listů.

Sytost se doplňuje konzumací jídla. Některé se dá jíst přímo, jiné se musí připravit z více surovin.
Neuvařené jídlo může být škodlivé.

Výčet některých jídel:

Ovoce/Zelenina: +0.5 sytosti (většinou)
Bonbónek: +1.5 sytosti
Smažené vejce: +1.5 sytosti
Maso na klacku: +2.0 sytosti
Polévka: +4.0 sytosti
Chléb: +4.0 sytosti

Suroviny
--------

Nejzákladnějšími surovinami je dřevo, kámen, zlatá a železná ruda. Nejlepší zbraně a brnění se vyrábí ze železné rudy.
Existuje pět typů brnění: helma, kyrys, boty, štít a rukavice. Nejvyšší ochranu poskytují štít a kyrys.

Základní recepty
----------------

Bojová tyč: 2 dřeva + listí
Malý nožík: 2 dřeva + opracovaný kámen
Dřevěnný štít: 3 dřeva + opracovaný kámen

Modding
-------

Pluginy pro hru se vkládají do složky plugins.

    L - načtení pluginů (load all)
    U - odebrání pluginů (unload all)
    R - reloadnutí pluginů (reload all)