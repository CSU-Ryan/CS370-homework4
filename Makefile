SRC_DIR := src
OUT_DIR := out
JC := javac
FLAGS := -cp $(SRC_DIR)/ -d $(OUT_DIR)/

all: build

build:
	mkdir -f $(OUT_DIR)
	$(JC) $(FLAGS) $(SRC_DIR)/*.java

clean:
	rm -rf $(OUT_DIR)/*