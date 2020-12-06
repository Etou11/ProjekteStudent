const whisperer = 
{
    phrases     : ["Optimization", "Follow-Up", "Quality", "Next generation", "Amazing"],

    printPhrase()
    {
        const { phrases } = this;
        const index = Math.floor(Math.random() * phrases.length);
        const phrase = phrases[index];
        console.log(phrase);
    }, 
    start()
    {
        this.timerID = setInterval(() => {
            printPhrase(this);
        }, 2000);
    },
    stop()
    {
        clearInterval(this.timerID);
        console.log("Thank You!")
    },

};