MAX_MISTAKES = 6

def inc(variable):
    return variable + 1

def replace_at_index(elements, index, new_element):
    copy_of_elements = []
    if index >= 0 and index < len(elements):
        for i in range(len(elements)):
            if i == index:
                copy_of_elements += [new_element]
            else:
                copy_of_elements += [elements[i]]
    return copy_of_elements

def add_to_list(elements, new_element):
    return elements + [new_element]

def index_of(word, element) -> list:
    indexes = []
    for i in range(len(word)):
        if word[i] == element:
            indexes = add_to_list(indexes, i)
    return indexes

def check_word(word, characters):
    return "".join(characters).replace("-", " ").lower() == word.replace("-", " ").lower()

def process_guess(word, characters, current_guess, guesses, mistakes):
    guess_indexes = index_of(word, current_guess.lower())
    if len(guess_indexes) == 0:
        mistakes = inc(mistakes)
    else:
        for index in guess_indexes:
            characters = replace_at_index(characters, index, current_guess)
    return (characters, add_to_list(guesses, current_guess), mistakes)

def display_game(characters, mistakes):
    print(" ".join(characters))
    print(f"mistakes: {mistakes}")

def clear_screen():
    print("\033[2J\033[H", end="")

def run_game(word):
    global MAX_MISTAKES
    characters = ["__" if character.isalpha() else "-" for character in word]
    guesses = []
    mistakes = 0
    victory = False

    display_game(characters, mistakes)

    while True:
        if check_word(word, characters):
            victory = True
            break
        if mistakes > MAX_MISTAKES:
            break
        current_guess = str(input("Make a guess: ")).strip()
        while current_guess in guesses:
            current_guess = str(input("You've already tried this one! Make another one: ")).strip()
        characters, guesses, mistakes = process_guess(word, characters, current_guess, guesses, mistakes)
        clear_screen()
        display_game(characters, mistakes)

    return victory

def main():
    victory = run_game("vasco da gama")
    if (victory):
        print("YOU WON!")
    else:
        print("SORRY FOR YOU!")

main()
