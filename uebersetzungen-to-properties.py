import csv
import os


def generate_files(csv_file_path,target_directory):
    with open(csv_file_path, newline='', encoding='utf-8') as csv_file:
        csv_reader = csv.DictReader(csv_file, delimiter=';')
       

        languages = next(csv_reader)
       
        languages.pop('propertyname')

       
        for row in csv_reader:
            prop_name= row['propertyname']
            for userLang, lines in row.items():
                if userLang == 'propertyname':
                    continue
                file_path = os.path.join(target_directory, f'messages_{userLang}.properties')
                with open(file_path, 'a' , encoding='utf-8') as file:
                    file.write(f"{prop_name}={lines}\n")

if __name__ == '__main__':
    csv_file_path = 'uebersetzungen.csv'
    target_directory = 'projekt/src/main/resources'
    if not os.path.exists(target_directory):
        os.makedirs(target_directory)
    generate_files(csv_file_path,target_directory)
    